package ar.com.tbf.common.data.accessibility;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;

public class AtributeExpressionAccessibility {

	private static ThreadLocal<AttributeExpression> attributeExpression = new ThreadLocal<AttributeExpression>() {
		
		protected AttributeExpression initialValue() {
			return new AtributeExpressionAccessibility().new AttributeExpression();
		}
	};

	private static final int CASE_INSENSITIVE = 1;
	

	public static boolean hasBuild( String attrName ) {
		
		return attributeExpression.get().attributes.containsKey(attrName);
	}
	
	public static Expression<?> build( String attrName, From<?, ?> from, CriteriaBuilder builder ) {
		
		Expression<?> expression = null;
		
		switch ( attributeExpression.get().attributes.get(attrName ) ) {
		
		case CASE_INSENSITIVE:
			
			expression = builder.lower( from.get(attrName) );
			break;

		default:
			break;
		}
		
		return expression;
	}
	
	class AttributeExpression {
		
		public Map<String, Integer> attributes = new HashMap<String, Integer>();
	}
}
