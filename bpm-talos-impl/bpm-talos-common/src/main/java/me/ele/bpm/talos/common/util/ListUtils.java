package me.ele.bpm.talos.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 跟list相关的工具类
 * @author yemengying
 *
 */
public class ListUtils {
	
	/**
	 * 从list中删除指定的元素 其他类需重写equals方法
	 * @param list
	 * @param arg 要删除的元素
	 * @return 返回删除了指定元素的list
	 * eg:list:[1,2,3,1]---removeElementFromList(list,1)---return list:[2,3]
	 */
	public static <T> List<T> removeElementFromList(List<T> list, T arg){
		if(null == list || list.isEmpty()) return new ArrayList<T>();
		if(arg == null) return list;
		return list.stream().filter(n -> {
	        return !n.equals(arg);
	    }).collect(Collectors.toList());
	}
	
	/**
	 * 打印list中的元素
	 * @param list
	 */
	public static <T> void printList(List<T> list){
		if(null == list) list = new ArrayList<T>();
		list.stream().forEach(n -> System.out.println(n.toString()));
	}
	
	/**
	 * list排序
	 * @param list
	 * @param comparator
	 * @return 返回按comparator排好序的list
	 * eg:User:id name两个属性 
	 *  List<User> userList = new ArrayList<User>();
	 *	userList.add(new User(1,"abc"));
	 *	userList.add(new User(3, "ccd"));
	 *	userList.add(new User(2, "bde"));
	 *	1.按user名字排序
	 *	userList = ListUtils.sortList(userList, (p1, p2) -> p1.getName().compareTo(p2.getName()));
	 *	2.按user Id排序
	 *  userList = ListUtils.sortList(userList, (p1, p2) -> p1.getId()-p2.getId());
	 */
	public static <T> List<T> sortList(List<T> list, Comparator<? super T> comparator){
		if(null == list || list.isEmpty()) return new ArrayList<T>();
		if(null == comparator) return list;
		return list.stream().sorted(comparator).collect(Collectors.toList());
		
	}										
	
	/**
	 * 判读list中的元素是不是全部满足 predicate的条件 
	 * @param list
	 * @param predicate
	 * @return 全部满足 true 有不满足的 false
	 * eg：判断list中的user的id是不是均小于4
	 * List<User> userList = new ArrayList<User>();
	 *	userList.add(new User(1,"abc"));
	 *	userList.add(new User(3, "ccd"));
	 *	userList.add(new User(2, "bde"));
	 *	System.out.println(ListUtils.isAllMatch(userList, u -> u.getId()<4));
	 *  输出 true
	 */
	public static <T> boolean isAllMatch(List<T> list, Predicate<? super T> predicate){
		if(null == list || list.isEmpty()) return false;
		if(null == predicate) return false;
		return list.stream().allMatch(predicate);
	}
	
	/**
	 * 只要有一个元素满足predicate的条件 返回true
	 * @param list
	 * @param predicate
	 * @return 
	 * eg：判断list中的user的id是不是有一个大于4
	 * List<User> userList = new ArrayList<User>();
	 *	userList.add(new User(1,"abc"));
	 *	userList.add(new User(3, "ccd"));
	 *	userList.add(new User(2, "bde"));
	 *	System.out.println(ListUtils.isAllMatch(userList, u -> u.getId()>4));  return false
	 */ 
	public static <T> boolean isAnyMatch(List<T> list, Predicate<? super T> predicate){
		if(null == list || list.isEmpty()) return false;
		if(null == predicate) return false;
		return list.stream().anyMatch(predicate);
	}
	
	/**
	 * 没有有一个元素满足predicate的条件 返回true
	 * @param list
	 * @param predicate
	 * @return
	 * eg：判断list中的user的id是不是有一个大于4
	 * List<User> userList = new ArrayList<User>();
	 *	userList.add(new User(1,"abc"));
	 *	userList.add(new User(3, "ccd"));
	 *	userList.add(new User(2, "bde"));
	 *	System.out.println(ListUtils.isAllMatch(userList, u -> u.getId()>4));  return true
	 */
	public static <T> boolean isNoneMatch(List<T> list, Predicate<? super T> predicate){
		if(null == list || list.isEmpty()) return false;
		if(null == predicate) return false;
		return list.stream().noneMatch(predicate);
	}
	
	/**
	 * list去重
	 * @param list
	 * @return
	 * eg:
	 * list[1,2,2]---distinctList(list)---list[1,2]
	 */
	public static <T> List<T> distinctList(List<T> list){
		if(null == list || list.isEmpty()) return new ArrayList<T>();
		return list.stream().distinct().collect(Collectors.toList());
	}
	
	/**
	 * 构造list
	 * @param args
	 * @return
	 * @author zhoujianming
	 */
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
	 * 递归获得多个list的笛卡尔积
	 * eg[1],[8808],[1,2,3]-->[[1,8808,1],[1,8808,2]]
	 * 参考：http://stackoverflow.com/questions/714108/cartesian-product-of-arbitrary-sets-in-java
	 * @param lists
	 * @return
	 */
	public static <T>  List<List<T>> cartesianProduct(List<List<T>> lists) {
	    List<List<T>> resultLists = new ArrayList<List<T>>();
	    if (lists.size() == 0) {
	        resultLists.add(new ArrayList<T>());
	        return resultLists;
	    } else {
	        List<T> firstList = lists.get(0);
	        List<List<T>> remainingLists = cartesianProduct(lists.subList(1, lists.size()));
	        for (T condition : firstList) {
	            for (List<T> remainingList : remainingLists) {
	                ArrayList<T> resultList = new ArrayList<T>();
	                resultList.add(condition);
	                resultList.addAll(remainingList);
	                resultLists.add(resultList);
	            }
	        }
	    }
	    return resultLists;
	}
	
	/**
	 * 当list含有以下元素中的任意一个 返回ture
	 * @param list
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean contains(List<T> list, T...args){
		if(null == list || null == args){
			return false;
		}
		for(T t : args){
			if(list.contains(t)) return true;
		}
		return false;
		
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
	
}
