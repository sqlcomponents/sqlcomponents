package ${rootPackage}.common;

import java.util.ArrayList;
import java.util.List;

public class PartialWhereClause  {

    protected final List<Object> nodes;

    protected PartialWhereClause () {
        this.nodes = new ArrayList<>();
    }
}