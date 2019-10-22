package ar.com.tbf.common.data.accessibility;

import java.util.HashMap;
import java.util.Map;

import ar.com.tbf.common.data.predicate.SpecificPredicate;

public class AttributePredicateHelperAccessibility {

	private static ThreadLocal<AttributePredicateHelperCollection> attributePredicateHelperCollection = new ThreadLocal<AttributePredicateHelperCollection>() {
		
		protected AttributePredicateHelperCollection initialValue() {
			return new AttributePredicateHelperAccessibility().new AttributePredicateHelperCollection();
		}
	};
	
	public static boolean has( String attributeName ) {
		
		return attributePredicateHelperCollection.get().attributes.containsKey( attributeName );
	}
	
	public static void add( String attributeName, SpecificPredicate specificPredicate ) {
		
		if( ! attributePredicateHelperCollection.get().attributes.containsKey( attributeName ) ) {
			
			attributePredicateHelperCollection.get().attributes.put(attributeName, specificPredicate);
		}
	}
	
	public static SpecificPredicate get( String attributeName ) {
		
		return attributePredicateHelperCollection.get().attributes.get( attributeName );
	}
	
	public static void setSilentMode( boolean silent ) {
		
		attributePredicateHelperCollection.get().setSilentMode(silent);
	}
	
	public static boolean isSilentMode( ) {
		
		return attributePredicateHelperCollection.get().isSilentMode();
	}
	public class AttributePredicateHelperCollection{
	
		/**
		 * Este atributo en true evita que se lance una exepción si el valor no se puede parsear, por ej si se busca con una letra en un campo numérico.
		 */
		private boolean silentMode = false;
		public Map<String, SpecificPredicate> attributes = new HashMap<String, SpecificPredicate>();
		
		public boolean isSilentMode() {
			return silentMode;
		}
		public void setSilentMode(boolean silentMode) {
			this.silentMode = silentMode;
		}

	}
	
}
