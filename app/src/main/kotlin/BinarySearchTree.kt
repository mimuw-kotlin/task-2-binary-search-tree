package pl.edu.mimuw.kotlin.task2

interface BinarySearchTree<K : Comparable<K>, V> {

  fun search(key: K): V?

  fun iterator(): Iterator<Pair<K, V>>
}

interface MutableBinarySearchTree<K : Comparable<K>, V> : BinarySearchTree<K, V> {

  fun insert(key: K, value: V)

  fun delete(key: K)

  fun asImmutable(): BinarySearchTree<K, V>
}

fun <K : Comparable<K>, V> emptyMutableBst(): MutableBinarySearchTree<K, V> {
  return TODO()
}
