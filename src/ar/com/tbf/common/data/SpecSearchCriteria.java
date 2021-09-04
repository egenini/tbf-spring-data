package ar.com.tbf.common.data;

public class SpecSearchCriteria {

	private String          key;
    private SearchOperation operation;
    private Object          value;
    private boolean         orPredicate;

    public SpecSearchCriteria() {
    }

    public SpecSearchCriteria(final String key, final SearchOperation operation, final Object value) {
    	
        super();
        
        this.key       = key;
        this.operation = operation;
        this.value     = value;
    }

    public SpecSearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Object value) {
    	
        super();
        
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key         = key;
        this.operation   = operation;
        this.value       = value;
    }

    public SpecSearchCriteria(String key, String operation, String prefix, Object value, String suffix) {
    	
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        Object finalValue = value;
        boolean isValueString = value instanceof String ? true : false;
        
        if (op != null) {
        
        	if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
        		
                final boolean startWithAsterisk = prefix != null && prefix.contains( SearchOperation.ZERO_OR_MORE_REGEX );
                final boolean endWithAsterisk   = suffix != null && suffix.contains( SearchOperation.ZERO_OR_MORE_REGEX );

                if ( startWithAsterisk && endWithAsterisk ) {
                	
                    op = SearchOperation.CONTAINS;
                    
                } else if ( startWithAsterisk ) {
                	
                    op = SearchOperation.STARTS_WITH;
                    
                } else if ( endWithAsterisk ) {
                	
                    op = SearchOperation.ENDS_WITH;
                }
            }
        	else if( op == SearchOperation.GREATER_THAN ) {
        		
                final boolean startWithAsterisk      = prefix != null && prefix.contains( SearchOperation.ZERO_OR_MORE_REGEX );
                final boolean valueStartWithAsterisk = value  != null && isValueString && ((String) value).contains( SearchOperation.ZERO_OR_MORE_REGEX );
                
                if ( startWithAsterisk ) {
                	
                    op = SearchOperation.GREATER_THAN_OR_EQUALS;
                }
                else if( valueStartWithAsterisk ) {
                	
                	op         = SearchOperation.GREATER_THAN_OR_EQUALS;
                	if( isValueString ){
                		finalValue = ((String) value).substring(1);
                	}
                }
        	}
        	else if( op == SearchOperation.LESS_THAN ) {
        		
                final boolean startWithAsterisk      = prefix != null && prefix.contains( SearchOperation.ZERO_OR_MORE_REGEX );
                final boolean valueStartWithAsterisk = value  != null && isValueString && ((String) value).contains( SearchOperation.ZERO_OR_MORE_REGEX );
        	
                if ( startWithAsterisk ) {
                	
                	op = SearchOperation.LESS_THAN_OR_EQUALS;
                }
                else if( valueStartWithAsterisk ) {
                	
                	op         = SearchOperation.LESS_THAN_OR_EQUALS;
                	if( isValueString ) {
                		finalValue = ((String) value).substring(1);                		
                	}
                }
        	}
        }
        
        this.key       = key;
        this.operation = op;
        this.value     = finalValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(final SearchOperation operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public boolean isOrPredicate() {
        return orPredicate;
    }

    public void setOrPredicate(boolean orPredicate) {
        this.orPredicate = orPredicate;
    }
}
