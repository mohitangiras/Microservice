package com.globomart.db;

import java.util.List;

public interface DB<K,V> {

	 V put(V value);
	 
	 V get(K key);
	 
	 V remove(K key);
	 
	 List<V> getAll();
}
