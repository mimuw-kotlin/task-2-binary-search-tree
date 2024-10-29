package pl.edu.mimuw.kotlin.task2

interface BinarySearchTree<K : Comparable<K>, V> {

  /**
   * Searches for a value associated with the given key.
   */
  fun search(key: K): V?

  /**
   * Returns an iterator over the elements of this tree in ascending order.
   */
  fun iterator(): Iterator<Pair<K, V>>
}

interface MutableBinarySearchTree<K : Comparable<K>, V> : BinarySearchTree<K, V> {

  /**
   * Inserts a key-value pair into the tree.
   */
  fun insert(key: K, value: V)

  /**
   * Deletes a key-value pair from the tree.
   */
  fun delete(key: K)

  /**
   * Returns an immutable copy of this tree.
   */
  fun asImmutable(): BinarySearchTree<K, V>
}

/**
 * Returns an empty mutable binary search tree.
 */
fun <K : Comparable<K>, V> emptyMutableBst(): MutableBinarySearchTree<K, V> {
  return TODO()
}
