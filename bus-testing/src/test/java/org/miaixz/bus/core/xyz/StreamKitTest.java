package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamKitTest {

    @Test
    void testIterateHierarchies() {
        // 创建一个三层的树结构，每个节点都有两个子节点
        final Node node = new Node("1", Arrays.asList(
                new Node("1-1", Arrays.asList(
                        new Node("1-1-1", null),
                        new Node("1-1-2", null)
                )),
                new Node("1-2", Arrays.asList(
                        new Node("1-2-1", null),
                        new Node("1-2-2", null)
                ))
        ));

        // 按广度度优先遍历树结构
        final List<String> allNodes = new ArrayList<>();
        StreamKit.iterateHierarchies(node, node1 -> node1.children)
                .forEach(node1 -> allNodes.add(node1.id));
        Assertions.assertEquals(Arrays.asList("1", "1-1", "1-2", "1-1-1", "1-1-2", "1-2-1", "1-2-2"), allNodes);

        // 按广度度优先遍历树结构，忽略id为1-1的节点与以其为根节点的子树
        final List<String> filteredNodes = new ArrayList<>();
        StreamKit.iterateHierarchies(node, node1 -> node1.children, node1 -> !node1.id.equals("1-1"))
                .forEach(node1 -> filteredNodes.add(node1.id));
        Assertions.assertEquals(Arrays.asList("1", "1-2", "1-2-1", "1-2-2"), filteredNodes);
    }

    @Test
    public void ofTest() {
        final Stream<Integer> stream = StreamKit.of(2, x -> x * 2, 4);
        final String result = stream.collect(CollectorKit.joining(","));
        Assertions.assertEquals("2,4,8,16", result);
    }

    // === iterator ===
    @Test
    public void streamTestNullIterator() {
        final Stream<Object> objectStream = StreamKit.ofIter(null);
        Assertions.assertEquals(0, objectStream.count());
    }

    @SuppressWarnings({"RedundantOperationOnEmptyContainer", "RedundantCollectionOperation"})
    @Test
    public void streamTestEmptyListToIterator() {
        assertStreamIsEmpty(StreamKit.ofIter(new ArrayList<>().iterator()));
    }

    @Test
    public void streamTestEmptyIterator() {
        assertStreamIsEmpty(StreamKit.ofIter(Collections.emptyIterator()));
    }

    @Test
    public void streamTestOrdinaryIterator() {
        final List<Integer> arrayList = ListKit.of(1, 2, 3);
        Assertions.assertArrayEquals(new Integer[]{1, 2, 3}, StreamKit.ofIter(arrayList.iterator()).toArray());

        final HashSet<Integer> hashSet = SetKit.of(1, 2, 3);
        Assertions.assertEquals(hashSet, StreamKit.ofIter(hashSet.iterator()).collect(Collectors.toSet()));
    }

    void assertStreamIsEmpty(final Stream<?> stream) {
        Assertions.assertNotNull(stream);
        Assertions.assertEquals(0, stream.toArray().length);
    }
    // ================ stream test end ================

    /**
     * 节点
     */
    private static class Node {
        private final String id;
        private List<Node> children;

        private Node(final String id, final List<Node> children) {
            this.id = id;
            this.children = children;
        }


        public Node(final String id) {
            this.id = id;
        }
    }
}
