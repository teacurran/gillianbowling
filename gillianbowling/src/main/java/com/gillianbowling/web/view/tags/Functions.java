package com.gillianbowling.web.view.tags;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.TransformingComparator;

/**
 * Date: 8/15/13
 *
 * @author T. Curran
 */
public class Functions {


	private Functions() {
	}

	public static String concat(
			final String string1,
			final String string2) {

		return concatVar(string1, string2);
	}

	public static String concat(
			final String string1,
			final String string2,
			final String string3) {

		return concatVar(string1, string2, string3);
	}

	public static String concatVar(final String... inStrings) {

		StringBuilder sb = new StringBuilder();
		for (String string : inStrings) {
			sb.append(string);
		}
		return sb.toString();
	}

	public static List<Object> sort(
			List<Object> inList,
			String inSortBy) {

		if (inList == null) {
			return new ArrayList<>();
		}

		Transformer lowercaseTransformer = new Transformer() {
			@Override
			public Object transform(
					final Object input) {

				if (input == null) {
					return null;
				}
				if (!(input instanceof String)) {
					return input;
				}
				return ((String) input).toLowerCase(Locale.ENGLISH);
			}
		};
		final Comparator lowercaseComparator =
				new TransformingComparator(lowercaseTransformer);
		final NullComparator nullComparator = new NullComparator(lowercaseComparator);

		@SuppressWarnings("unchecked")
		final Comparator<Object> beanComparator =
				new BeanComparator(inSortBy, nullComparator);

		Collections.sort(inList, beanComparator);

		return inList;
	}

	public static String urlEncode(final String inValue) {

		try {
			return URLEncoder.encode(inValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return inValue;
		}
	}
}
