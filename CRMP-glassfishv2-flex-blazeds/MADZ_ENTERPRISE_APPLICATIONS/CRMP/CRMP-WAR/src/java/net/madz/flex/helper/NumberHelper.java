/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.flex.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author CleaNEr
 */
public class NumberHelper {

	public static List<Long> toLongList(List numbers) {
		if (null == numbers || numbers.size() <= 0) {
			return new ArrayList<Long>();
		}
		List<Long> result = new ArrayList<Long>();
		Iterator iter = numbers.iterator();
		while (iter.hasNext()) {
			result.add(Long.valueOf(iter.next().toString()));
		}
		return result;
	}
}
