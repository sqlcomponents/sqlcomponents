#!/usr/bin/env bash
#
# Vendors tamilnadujug/sql-builder Java sources into FreeMarker templates under
#   compiler/src/main/resources/template/java/
# (same tree as other compiler templates — no vendor/sql-builder/ prefix).
#
# Layout (mirrors org/tamilnadujug under the sql-builder repo):
#   SqlBuilder.java     -> template/java/SqlBuilder.ftl   (replaces any existing)
#   sql/Sql.java        -> template/java/sql/Sql.ftl
#   sql/RowMapper.java  -> template/java/sql/RowMapper.ftl
#   …
#
# - Replaces `package org.tamilnadujug...` with a rootPackage-aware FTL line
#   aligned to the relative path (same as upstream Java packages).
# - Rewrites `import` / `import static` lines from org.tamilnadujug to ${rootPackage}.
#
# Run from anywhere; paths are resolved from this script's location.
# Requires: curl, unzip, awk, sed, find.
#
# After vendoring: add JavaCompiler passes to process each .ftl and write .java
# under srcFolder (Application must expose rootPackage). Update Manager.ftl /
# Store.ftl imports off org.tamilnadujug if you emit types under rootPackage.
#
set -euo pipefail

REPO_ZIP_URL="${REPO_ZIP_URL:-https://github.com/tamilnadujug/sql-builder/archive/refs/heads/main.zip}"
ZIP_FILE="${ZIP_FILE:-sql-builder-vendor.zip}"
EXTRACT_DIR="${EXTRACT_DIR:-sql-builder-main}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COMPILER_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
REPO_ROOT="$(cd "${COMPILER_DIR}/.." && pwd)"

SOURCE_DIR="${EXTRACT_DIR}/src/main/java/org/tamilnadujug"
TARGET_BASE="${COMPILER_DIR}/src/main/resources/template/java"

echo "Repo root (reference): ${REPO_ROOT}"
echo "Compiler module:     ${COMPILER_DIR}"
echo "Target templates:    ${TARGET_BASE}/"
echo "(sql-builder wins: overwrites template/java/SqlBuilder.ftl if present)"

cd "${COMPILER_DIR}"

echo "Downloading ${REPO_ZIP_URL} ..."
curl -fsSL "${REPO_ZIP_URL}" -o "${ZIP_FILE}"

echo "Extracting ${ZIP_FILE} ..."
rm -rf "${EXTRACT_DIR}"
unzip -q -o "${ZIP_FILE}"

if [[ ! -d "${SOURCE_DIR}" ]]; then
  echo "ERROR: Expected directory not found: ${SOURCE_DIR}" >&2
  echo "Check EXTRACT_DIR / upstream layout." >&2
  exit 1
fi

echo "Writing .ftl files under template/java/ (mirroring org/tamilnadujug) ..."
mkdir -p "${TARGET_BASE}"

# Drop previous script output location to avoid duplicate SqlBuilder.ftl trees.
LEGACY_VENDOR="${TARGET_BASE}/vendor/sql-builder"
if [[ -d "${LEGACY_VENDOR}" ]]; then
  echo "Removing legacy ${LEGACY_VENDOR} ..."
  rm -rf "${LEGACY_VENDOR}"
fi

# Process each .java (skip module-info and package-info)
while IFS= read -r -d '' file; do
  base="${file#"${SOURCE_DIR}"/}"
  rel_java="${base%.java}"
  rel_dir="$(dirname "${rel_java}")"
  if [[ "${rel_dir}" == "." ]]; then
    dotted_subpkg=""
  else
    dotted_subpkg="${rel_dir//\//.}"
  fi

  if [[ -z "${dotted_subpkg}" ]]; then
    pkg_line='<#if rootPackage?? && rootPackage?length != 0>package ${rootPackage};</#if>'
  else
    pkg_line="<#if rootPackage?? && rootPackage?length != 0>package \${rootPackage}.${dotted_subpkg};</#if>"
  fi

  new_path="${TARGET_BASE}/${rel_java}.ftl"
  mkdir -p "$(dirname "${new_path}")"

  # awk: replacement from env so ${rootPackage} is not interpreted by sed.
  export SQLC_VENDOR_PKG_LINE="${pkg_line}"
  awk '
    BEGIN { r = ENVIRON["SQLC_VENDOR_PKG_LINE"] }
    /^package[[:space:]]+org\.tamilnadujug/ {
      if (!seen) { print r; seen = 1; next }
    }
    { print }
  ' "${file}" \
    | sed -e 's/^import static org\.tamilnadujug/import static ${rootPackage}/' \
          -e 's/^import org\.tamilnadujug/import ${rootPackage}/' \
    > "${new_path}"

  echo "  wrote ${new_path#"${COMPILER_DIR}"/}"

done < <(find "${SOURCE_DIR}" -type f -name '*.java' \
  ! -name 'module-info.java' \
  ! -name 'package-info.java' -print0)

echo "Cleaning up archive and extract tree ..."
rm -rf "${ZIP_FILE}" "${EXTRACT_DIR}"

echo "Done. Templates are under: ${TARGET_BASE}/"
echo "Note: FreeMarker must see rootPackage on the root model when these run."
echo "Note: JavaCompiler must explicitly process each .ftl to emit matching .java files."
