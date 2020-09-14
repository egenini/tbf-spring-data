package ar.com.tbf.common.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;

public class CriteriaParser {

	private static Map<String, Operator> ops;

	private static Pattern MAY_BE_DATE_TIME_PATTERN = Pattern.compile(
		      "^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|"
		      + "(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?"
		      + "(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$" + 
		      "");
	
	private static Pattern SpecCriteraRegex = Pattern.compile("^([a-zA-Z_\\\\.]+?)(" + Joiner.on("|")
	.join(SearchOperation.SIMPLE_OPERATION_SET) + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?)$");

	private static Pattern operationPattern = Pattern.compile("^(\\w+?)(" + Joiner.on("|")
	.join(SearchOperation.SIMPLE_OPERATION_SET) + ")(\\w+|\\*\\w+)");

	private enum Operator {
		OR(1), AND(2);
		final int precedence;

		Operator(int p) {
			precedence = p;
		}
	}

	static {
		Map<String, Operator> tempMap = new HashMap<>();
		tempMap.put("AND", Operator.AND);
		tempMap.put("OR", Operator.OR);
		tempMap.put("or", Operator.OR);
		tempMap.put("and", Operator.AND);

		ops = Collections.unmodifiableMap(tempMap);
	}

	private static boolean isHigerPrecedenceOperator(String currOp, String prevOp) {
		return (ops.containsKey(prevOp) && ops.get(prevOp).precedence >= ops.get(currOp).precedence);
	}

	public Deque<?> parse(String searchParam) {

		Deque<Object> output = new LinkedList<>();
		Deque<String> stack = new LinkedList<>();

		Arrays.stream(searchParam.split("\\s+")).forEach(token -> {
			if (ops.containsKey(token)) {
				while (!stack.isEmpty() && isHigerPrecedenceOperator(token, stack.peek()))
					output.push(stack.pop()
							.equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
				stack.push(token.equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
			} else if (token.equals(SearchOperation.LEFT_PARANTHESIS)) {
				stack.push(SearchOperation.LEFT_PARANTHESIS);
			} else if (token.equals(SearchOperation.RIGHT_PARANTHESIS)) {
				while (!stack.peek()
						.equals(SearchOperation.LEFT_PARANTHESIS))
					output.push(stack.pop());
				stack.pop();
			} else {

				if( isEmail( token ) ) {
					
					Matcher matcher = operationPattern.matcher(token);
					while (matcher.find()) {
						output.push( new SpecSearchCriteria( matcher.group(1), matcher.group(2), null, token.substring(token.indexOf(matcher.group(2))+1), null ) );
					}
				}
				
				else if( isDate(token) ) {
					
					Matcher matcher = operationPattern.matcher(token);
					//Matcher matcher = SpecCriteraRegex.matcher(token);
					while (matcher.find()) {
						//output.push( new SpecSearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5)));
						output.push( new SpecSearchCriteria( matcher.group(1), matcher.group(2), null, 
								token.substring(token.indexOf(matcher.group(2))+1), null ) );
					}
				}
				
				else {
					
					Matcher matcher = SpecCriteraRegex.matcher(token);
					while (matcher.find()) {
						output.push( new SpecSearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5)));
					}
				}
			}
		});

		while (!stack.isEmpty())
			output.push(stack.pop());

		return output;
	}
	
	private boolean isEmail( String token ) {
		
		int pos = token.indexOf("@");
		
		return  pos != -1 && pos >= 2 && token.substring(pos).contains( "." ); 
	}
	
	private boolean isDate( String token ) {
		
		boolean maybe = false;
		
		String s[] = token.split("-");
		
		maybe = s.length > 2;
		
		if( ! maybe ) {
			
			s = token.split("/");
			
			maybe = s.length > 2;
		}
		
		return maybe;
	}
}
