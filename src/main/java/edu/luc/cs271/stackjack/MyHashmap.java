//package edu.luc.cs271.myhashmap;

import java.util.*;

/**
 * A generic HashMap custom implementation using chaining. Concretely, the table is an ArrayList of
 * chains represented as LinkedLists.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class MyHashmap<K, V> implements Map<K, V> {

  private static final int DEFAULT_TABLE_SIZE = 52; // prime number size of standard deck

  private List<List<Entry<K, V>>> table;

  public MyHashmap() {
    this(DEFAULT_TABLE_SIZE);
  }

   public MyHashmap(final int tableSize) {
    // allocate a table of the given size
    table = new ArrayList<>(tableSize);
    // then create an empty chain at each position
    for (int i = 0; i < tableSize; i++) {
      table.add(new LinkedList<>());
    }
   }

  @Override
  public int size() {
    // TODO add the sizes of all the chains
    int result = 0;
    for (int i = 0; i < table.size(); i++) {
      result += table.get(i).size();
    }
    return result;
  }

  @Override
  public boolean isEmpty() {
    if (size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean containsKey(final Object key) {
    // DONE
    // TODO follow basic approach of remove below (though this will be much simpler)
    final int index = calculateIndex(key);
    final Iterator<Entry<K, V>> iter = table.get(index).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      if (entry.getKey().equals(key)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsValue(final Object value) {
    // TODO follow basic approach of remove below (though this will be much simpler)
    for (int i = 0; i < table.size(); i++) {
      final Iterator<Entry<K, V>> iter = table.get(i).iterator();
      while (iter.hasNext()) {
        final Entry<K, V> entry = iter.next();
        if (entry.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public V get(final Object key) {
    // DONE
    // TODO follow basic approach of remove below (though this will be simpler)
    final int index = calculateIndex(key);
    final Iterator<Entry<K, V>> iter = table.get(index).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      if (entry.getKey().equals(key)) {
        return entry.getValue();
      }
    }
    return null;
  }

  @Override
  public V put(final K key, final V value) {
    // TODO follow basic approach of remove below (this will be similar)
    // DONE
    int index = calculateIndex(key);
    if (table.get(index) == null) {
      table.add(index, new LinkedList<>());
    }
    final Iterator<Entry<K, V>> iter = table.get(index).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      if (entry.getKey().equals(key)) {
        final V oldValue = entry.getValue();
        entry.setValue(value);
        return oldValue;
      }
    }
    AbstractMap.SimpleEntry<K, V> entry = new AbstractMap.SimpleEntry<K, V>(key, value);
    table.get(index).add(entry);
    return null;
  }

  @Override
  public V remove(final Object key) {
    // calculates index using hashcode % table.length
    final int index = calculateIndex(key);
    final Iterator<Entry<K, V>> iter = table.get(index).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      // key is found
      if (entry.getKey().equals(key)) {
        final V oldValue = entry.getValue();
        // remove value of the key that was found
        iter.remove();
        // return the value that was removed
        return oldValue;
      }
    }
    // key is not in table
    return null;
  }

  @Override
  public void putAll(final Map<? extends K, ? extends V> m) {
    // TODO add each entry in m's entrySet
    for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    // TODO clear each chain
    for (int i = 0; i < table.size(); i++) {
      final Iterator<Entry<K, V>> iter = table.get(i).iterator();
      while (iter.hasNext()) {
        final Entry<K, V> entry = iter.next();
        remove(entry.getKey());
      }
    }
  }

  /** The resulting keySet is not "backed" by the Map, so we keep it unmodifiable. */
  @Override
  public Set<K> keySet() {
    final Set<K> result = new HashSet<>();
    // TODO populate the set
    for (int i = 0; i < table.size(); i++) {
      final Iterator<Entry<K, V>> iter = table.get(i).iterator();
      while (iter.hasNext()) {
        final Entry<K, V> entry = iter.next();
        result.add(entry.getKey());
      }
    }
    return Collections.unmodifiableSet(result);
  }

  /** The resulting values collection is not "backed" by the Map, so we keep it unmodifiable. */
  @Override
  public Collection<V> values() {
    final List<V> result = new LinkedList<>();
    for (int i = 0; i < table.size(); i++) {
      final Iterator<Entry<K, V>> iter = table.get(i).iterator();
      while (iter.hasNext()) {
        final Entry<K, V> entry = iter.next();
        result.add(entry.getValue());
      }
    }
    return Collections.unmodifiableCollection(result);
  }

  /** The resulting entrySet is not "backed" by the Map, so we keep it unmodifiable. */
  @Override
  public Set<Entry<K, V>> entrySet() {
    final Set<Entry<K, V>> result = new HashSet<>();
    // TODO populate the set
    for (int i = 0; i < table.size(); i++) {
      final Iterator<Entry<K, V>> iter = table.get(i).iterator();
      while (iter.hasNext()) {
        result.add(iter.next());
      }
    }

    return Collections.unmodifiableSet(result);
  }

  private int calculateIndex(final Object key) {
    // positive remainder (as opposed to %)
    // required in case hashCode is negative!
    double b = Math.sqrt(5);
    double a = (b-1)/2;
    double ka = key.hashCode() * a;
    return (int) Math.floor(table.size()*(ka - Math.floor(ka)));
  }
}
//m:table.size();
//k:key.hashCode();
//A: some constant such that 0 < A < 1