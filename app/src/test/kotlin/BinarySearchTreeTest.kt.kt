import pl.edu.mimuw.kotlin.task2.BinarySearchTree
import pl.edu.mimuw.kotlin.task2.MutableBinarySearchTree
import pl.edu.mimuw.kotlin.task2.emptyMutableBst
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertFalse

class BinarySearchTreeTest {

  private lateinit var bst: MutableBinarySearchTree<Int, String>

  @BeforeTest
  fun setup() {
    bst = emptyMutableBst()
  }

  @Test
  fun testInsertAndSearch() {
    // Insert elements
    bst.insert(50, "Apple")
    bst.insert(30, "Banana")
    bst.insert(70, "Cherry")
    bst.insert(20, "Date")
    bst.insert(40, "Elderberry")
    bst.insert(60, "Fig")
    bst.insert(80, "Grape")

    // Search for existing keys
    assertEquals("Apple", bst.search(50))
    assertEquals("Banana", bst.search(30))
    assertEquals("Cherry", bst.search(70))
    assertEquals("Date", bst.search(20))
    assertEquals("Elderberry", bst.search(40))
    assertEquals("Fig", bst.search(60))
    assertEquals("Grape", bst.search(80))

    // Search for non-existing keys
    assertNull(bst.search(25))
    assertNull(bst.search(90))
  }

  @Test
  fun testUpdateExistingKey() {
    // Insert a key
    bst.insert(10, "Initial")
    assertEquals("Initial", bst.search(10))

    // Update the same key
    bst.insert(10, "Updated")
    assertEquals("Updated", bst.search(10))
  }

  @Test
  fun testDeleteLeafNode() {
    // Insert elements
    bst.insert(50, "Apple")
    bst.insert(30, "Banana")
    bst.insert(70, "Cherry")
    bst.insert(20, "Date") // Leaf node

    // Delete leaf node
    bst.delete(20)
    assertNull(bst.search(20))

    // Ensure other nodes are unaffected
    assertEquals("Apple", bst.search(50))
    assertEquals("Banana", bst.search(30))
    assertEquals("Cherry", bst.search(70))
  }

  @Test
  fun testDeleteNodeWithOneChild() {
    // Insert elements
    bst.insert(50, "Apple")
    bst.insert(30, "Banana")
    bst.insert(70, "Cherry")
    bst.insert(20, "Date")
    bst.insert(25, "Elderberry") // Node 20 has one child (25)

    // Delete node with one child
    bst.delete(20)
    assertNull(bst.search(20))
    assertEquals("Elderberry", bst.search(25))

    // Ensure tree structure is correct
    assertEquals("Banana", bst.search(30))
  }

  @Test
  fun testDeleteNodeWithTwoChildren() {
    // Insert elements
    bst.insert(50, "Apple")
    bst.insert(30, "Banana")
    bst.insert(70, "Cherry")
    bst.insert(20, "Date")
    bst.insert(40, "Elderberry")
    bst.insert(60, "Fig")
    bst.insert(80, "Grape")

    // Delete node with two children
    bst.delete(30)
    assertNull(bst.search(30))
    assertEquals("Date", bst.search(20))
    assertEquals("Elderberry", bst.search(40))

    // Ensure in-order successor replaced the deleted node
    // In this case, the in-order successor of 30 is 40
    // So, 40 should now be in place of 30
    // Since our implementation replaces key and value, searching for 40 should still return "Elderberry"
    assertEquals("Elderberry", bst.search(40))
  }

  @Test
  fun testDeleteRootNode() {
    // Insert elements
    bst.insert(50, "Apple")
    bst.insert(30, "Banana")
    bst.insert(70, "Cherry")

    // Delete root node which has two children
    bst.delete(50)
    assertNull(bst.search(50))

    // The in-order successor of 50 is 60 if it exists, but since 60 is not inserted,
    // the next smallest is 70's leftmost, which is 70 itself.
    // So, 70 should replace 50
    assertEquals("Cherry", bst.search(70))
    assertEquals("Banana", bst.search(30))
  }

  @Test
  fun testDeleteNonExistingKey() {
    // Insert elements
    bst.insert(10, "A")
    bst.insert(20, "B")
    bst.insert(30, "C")

    // Delete a non-existing key
    bst.delete(40) // Should not throw an error

    // Ensure existing keys are unaffected
    assertEquals("A", bst.search(10))
    assertEquals("B", bst.search(20))
    assertEquals("C", bst.search(30))
  }

  @Test
  fun testIterateInOrder() {
    // Insert elements
    val elements = listOf(
      50 to "Apple",
      30 to "Banana",
      70 to "Cherry",
      20 to "Date",
      40 to "Elderberry",
      60 to "Fig",
      80 to "Grape"
    )
    elements.forEach { (key, value) -> bst.insert(key, value) }

    // Expected in-order traversal
    val expected = listOf(
      20 to "Date",
      30 to "Banana",
      40 to "Elderberry",
      50 to "Apple",
      60 to "Fig",
      70 to "Cherry",
      80 to "Grape"
    )

    val result = bst.iterator().asSequence().toList()
    assertEquals(expected, result)
  }

  @Test
  fun testIterateEmptyTree() {
    // Iterate over an empty tree
    val iterator = bst.iterator()
    assertFalse(iterator.hasNext())
  }

  @Test
  fun testIterateSingleElement() {
    // Insert a single element
    bst.insert(100, "Solo")

    // Iterate and verify
    val expected = listOf(100 to "Solo")
    val result = bst.iterator().asSequence().toList()
    assertEquals(expected, result)
  }

  @Test
  fun testMultipleInsertionsAndDeletions() {
    // Insert multiple elements
    val elements = listOf(
      15 to "Fifteen",
      10 to "Ten",
      20 to "Twenty",
      8 to "Eight",
      12 to "Twelve",
      17 to "Seventeen",
      25 to "Twenty-Five"
    )
    elements.forEach { (key, value) -> bst.insert(key, value) }

    // Delete some elements
    bst.delete(10) // Node with two children
    bst.delete(20) // Node with two children
    bst.delete(8)  // Leaf node

    // Expected remaining elements after deletions
    val expected = listOf(
      12 to "Twelve",
      15 to "Fifteen",
      17 to "Seventeen",
      25 to "Twenty-Five"
    )

    val result = bst.iterator().asSequence().toList()
    assertEquals(expected, result)

    // Verify deleted elements
    assertNull(bst.search(10))
    assertNull(bst.search(20))
    assertNull(bst.search(8))
  }

  @Test
  fun testInsertDuplicateKeys() {
    // Insert duplicate keys with different values
    bst.insert(5, "Five")
    bst.insert(3, "Three")
    bst.insert(7, "Seven")
    bst.insert(3, "Three Updated")
    bst.insert(7, "Seven Updated")

    // Verify that values are updated
    assertEquals("Five", bst.search(5))
    assertEquals("Three Updated", bst.search(3))
    assertEquals("Seven Updated", bst.search(7))

    // Expected in-order traversal
    val expected = listOf(
      3 to "Three Updated",
      5 to "Five",
      7 to "Seven Updated"
    )

    val result = bst.iterator().asSequence().toList()
    assertEquals(expected, result)
  }

  @Test
  fun testLargeNumberOfInsertions() {
    // Insert a large number of elements
    val numElements = 1000
    for (i in 1..numElements) {
      bst.insert(i, "Value $i")
    }

    // Verify all elements are present
    for (i in 1..numElements) {
      assertEquals("Value $i", bst.search(i))
    }

    // Verify in-order traversal
    val expected = (1..numElements).map { it to "Value $it" }
    val result = bst.iterator().asSequence().toList()
    assertEquals(expected, result)
  }


  @Test
  fun testMultipleDeletions() {
    // Insert elements
    val elements = listOf(50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85)
    elements.forEach { bst.insert(it, "Value $it") }

    // Delete multiple keys
    val keysToDelete = listOf(20, 30, 50, 70)
    keysToDelete.forEach { bst.delete(it) }

    // Verify deletions
    keysToDelete.forEach { assertNull(bst.search(it)) }

    // Verify remaining elements
    val remaining = listOf(
      10 to "Value 10",
      25 to "Value 25",
      35 to "Value 35",
      40 to "Value 40",
      45 to "Value 45",
      55 to "Value 55",
      60 to "Value 60",
      65 to "Value 65",
      75 to "Value 75",
      80 to "Value 80",
      85 to "Value 85"
    )
    val result = bst.iterator().asSequence().toList()
    assertEquals(remaining, result)
  }

  // This one is a bit tricky:
  // If you get `Expected an exception of class java.lang.NoSuchMethodException to be thrown, but was completed successfully.`
  // remember that just casting is not enough. You need to wrap the mutable BST in a wrapper to actually hide the mutable methods.
  // But also remember that code duplication is bad.
  // If you get stuck, read https://kotlinlang.org/docs/delegation.html
  @Test
  fun testMutableOperationsOnImmutableTree() {
    // Insert elements into the mutable BST
    bst.insert(10, "Ten")
    bst.insert(20, "Twenty")
    bst.insert(30, "Thirty")

    // Convert to immutable BST
    val immutableBst: BinarySearchTree<Int, String> = bst.asImmutable()

    // Ensure that BST contains the elements
    assertEquals("Ten", immutableBst.search(10))
    assertEquals("Twenty", immutableBst.search(20))
    assertEquals("Thirty", immutableBst.search(30))

    // Check that the cast fails
    assertFailsWith<ClassCastException> {
      immutableBst as MutableBinarySearchTree<Int, String>
    }

    // Get the insert method using reflection (because we can't test if it doesn't compile)
    assertFailsWith<NoSuchMethodException> {
      immutableBst::class.java.getMethod("insert", Comparable::class.java, Any::class.java)
    }

    assertFailsWith<NoSuchMethodException> {
      immutableBst::class.java.getMethod("delete", Comparable::class.java)
    }
  }
}
