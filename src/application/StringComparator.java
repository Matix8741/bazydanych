/**
 * 
 */
package application;

import java.util.Comparator;

/**
 * @author Mateusz
 *
 */
public class StringComparator implements Comparator<String> {

	String pattern;
	/**
	 * 
	 */
	public StringComparator(String pattern) {
		this.pattern = pattern;
	}
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(String o1, String o2) {
		System.out.println(o1+"  > "+o2);
		char[] patternArray = pattern.toCharArray();
		char[] firstWord = o1.toCharArray();
		char[] secondWord = o2.toCharArray();
		boolean f= true,s=true;
		int firstCount =0, secondCount =0;
		int i =0;
		for(char ch : patternArray){
			if(firstWord.length > i&&f){
				if(firstWord[i] == ch){
					firstCount++;
					System.out.println(firstWord[i]+" ++ "+ch);
				}
				else{
					f = false;
				}
			}
			if(secondWord.length > i&&s){
				if(secondWord[i] == ch){
					secondCount++;
					System.out.println(secondWord[i]+" ++ "+ch);
				}
				else{
					s = false;
				}
			}
		}
 		return (secondCount - firstCount);
	}

}
