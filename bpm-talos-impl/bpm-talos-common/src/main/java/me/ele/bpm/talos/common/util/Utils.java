package me.ele.bpm.talos.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.ele.bpm.talos.common.model.Pair;

/**
 * 这个工具类用来方便处理一些基础数据、基础数据结构的常见的判断和转换。
 * 比如String相关的有isEmpty、killNull、isEmptyWithTrim函数。 目前还包含List的一些变换函数。 这个类还在不断完善中。
 * 
 * 这个类的编写原则就是：只依赖于Java基础库、以及sge自己的基础库。
 * 
 */
public class Utils {
	public static boolean isEmpty(String s) {
		if (s == null)
			return true;
		if ("".equals(s))
			return true;
		return false;
	}

	public static boolean isEmptyWithTrim(String s) {
		if (s == null)
			return true;
		if (s.trim().equals(""))
			return true;
		return false;
	}

	public static String killNull(String s) {
		if (s == null)
			return "";
		else
			return s;
	}

	public static String safeToString(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	public static boolean equals(String strA, String strB) {
		if (strA == null) {
			if (strB == null)
				return true;
			else
				return false;
		} else {
			return strA.equals(strB);
		}
	}

	public static int[] toArray(List<Integer> list) {
		if (list == null)
			return new int[0];
		int[] array = new int[list.size()];

		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}

		return array;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(T...args) {
		if (null == args) {
			return new ArrayList<T>();
		}
		List<T> list = new ArrayList<T>();
		for (T t : args) {
			list.add(t);
		}
		return list;
	}
	
	/**
	 * 转换器。配合Utils.transform函数，对列表内容进行变换。
	 * 
	 * @param <E>
	 *            原对象
	 * @param <T>
	 *            转换后的对象
	 */
	public static interface Transformer<E, T> {
		public T transform(E e);
	}

	/**
	 * 对List的内容进行变换
	 * 
	 * @param elist
	 *            包含类型为E的对象的列表
	 * @param transformer
	 *            转换器
	 * @return 包含类型为T的对象的列表
	 */
	public static <E, T> List<T> transform(Collection<E> elist,
			Transformer<E, T> transformer) {
		List<T> tlist = new ArrayList<T>();
		if (elist == null)
			return tlist;
		for (E e : elist) {
			if (e == null) {
				tlist.add(null);
			} else {
				tlist.add(transformer.transform(e));
			}
		}
		return tlist;
	}
	
	/**
	 * 转换器 配合Utils.filterSame函数，对列表数据进行去重
	 * 
	 * @param <K>
	 * @param <T>
	 */
	public static interface FilterSamer<K, T> {
		public Pair<K, T> filterSame(T t);
	}
	
	/**
	 * 对List中数据根据"主键"去重
	 * 
	 * @param tlist
	 * @param filterSamer	转换器
	 * @return	返回tlist中去掉重复部分的数据（如果有重复，后者会覆盖前者）
	 */
	public static <K, T> List<T> filterSame(Collection<T> tlist,
			FilterSamer<K, T> filterSamer) {
		List<T> list = new ArrayList<T>();
		Map<K, T> map = new LinkedHashMap<K, T>();
		if (tlist == null || tlist.isEmpty())
			return list;
		for (T t : tlist) {
			if (t != null) {
				Pair<K, T> pair = filterSamer.filterSame(t);
				map.put(pair.first, pair.second);
			}
		}
		for (K key : map.keySet()) {
			list.add(map.get(key));
		}
		return list;
	}

	public static String convertToString(List<?> list, String separator) {
		if (list == null || list.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			sb.append(separator);
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	// /////////////////////////////////

	private static NumberFormat formatter = NumberFormat
			.getInstance(new Locale("en_US"));

	public static final String comma(double number) {
		return formatter.format(number);
	}

	public static final String comma(long number) {
		return formatter.format(number);
	}

	public static final String comma(Object number) {
		return formatter.format(number);
	}

	public static String comma(BigDecimal number, int scale) {
		return formatter.format(number
				.setScale(scale, BigDecimal.ROUND_HALF_UP));
	}

	public static int compare(String a, String b) {
		return killNull(a).compareTo(killNull(b));
	}

	public static String prettyDouble(double number, int scale) {
		return new BigDecimal(number).setScale(scale, BigDecimal.ROUND_HALF_UP)
				.toPlainString();
	}

	public static <T> List<T> safeSubList(List<T> list, int from, int to) {
		if (list == null)
			return Collections.emptyList();
		if (list.size() == 0)
			return list;
		if (from > list.size())
			return Collections.emptyList();
		if (from < 0)
			return Collections.emptyList();
		if (to <= from)
			return Collections.emptyList();
		if (to > list.size())
			to = list.size();
		return list.subList(from, to);
	}

	public static <E, T> List<T> simpleTransform(Collection<E> list,
			Class<T> requiredType) {
		if (String.class.equals(requiredType)) {
			return Utils.transform(list, new Transformer<E, T>() {
				@SuppressWarnings("unchecked")
				@Override
				public T transform(E e) {
					return (T) e.toString();
				}
			});
		} else if (Integer.class.equals(requiredType)) {
			return Utils.transform(list, new Transformer<E, T>() {

				@SuppressWarnings("unchecked")
				@Override
				public T transform(E e) {
					return (T) (Integer) Integer.parseInt(e.toString());
				}
			});
		} else {
			throw new IllegalArgumentException(
					"simpleTransform only support String and Integer.");
		}
	}

}
