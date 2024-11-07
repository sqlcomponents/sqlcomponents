 /**
     * Class for building the SQL WhereClause.
     */
    public static class WhereClause<T extends Column<?>> extends PartialWhereClause<T> {
        public WhereClause() {
            super();
        }

        public String asSql() {
            return nodes.isEmpty() ? null : nodes.stream().map(node -> {
                if (node instanceof Column) {
                    return ((Column<?>) node).asSql();
                } else if (node instanceof WhereClause) {
                    return "(" + ((WhereClause<T>) node).asSql() + ")";
                } else {
                    return (String) node;
                }
            }).collect(Collectors.joining(" "));
        }

        /**
         * Adds "AND" to the clause.
         *
         * @return This PartialWhereClause instance.
         */
        public PartialWhereClause<T> and() {
            this.nodes.add("AND");
            return this;
        }

        /**
         * Adds "OR" to the clause.
         *
         * @return This PartialWhereClause instance.
         */
        public PartialWhereClause<T> or() {
            this.nodes.add("OR");
            return this;
        }

        /**
         * Adds "AND" followed by the given WhereClause.
         *
         * @param whereClause The WhereClause to add.
         * @return This WhereClause instance.
         */
        public WhereClause<T> and(final WhereClause<T> whereClause) {
            this.nodes.add("AND");
            this.nodes.add(whereClause);
            return this;
        }

        /**
         * Adds "OR" followed by the given WhereClause.
         *
         * @param whereClause The WhereClause to add.
         * @return This WhereClause instance.
         */
        public WhereClause<T> or(final WhereClause<T> whereClause) {
            this.nodes.add("OR");
            this.nodes.add(whereClause);
            return this;
        }

    }

    /**
     * Partial SQL WhereClause.
     */
    public static class PartialWhereClause<T extends Column<?>>  {

        protected final List<Object> nodes;

        public PartialWhereClause() {
            this.nodes = new ArrayList<>();
        }

        public void add(final T column) {
            this.nodes.add(column);
        }
    }

<#assign a=addImportStatement("java.util.stream.Collectors")>
    