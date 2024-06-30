package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.stream.EasyStream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorKitTest {

    @Test
    public void reduceListMapTest() {
        final Set<Map<String, Integer>> nameScoreMapList = StreamKit.of(
                // 集合内的第一个map，包含两个key value
                MapKit.builder("苏格拉底", 1).put("特拉叙马霍斯", 3).build(),
                MapKit.of("苏格拉底", 2),
                MapKit.of("特拉叙马霍斯", 1),
                MapKit.of("特拉叙马霍斯", 2)
        ).collect(Collectors.toSet());
        // 执行聚合
        final Map<String, List<Integer>> nameScoresMap = nameScoreMapList.stream().collect(CollectorKit.reduceListMap());

        Assertions.assertEquals(MapKit.builder("苏格拉底", Arrays.asList(1, 2))
                        .put("特拉叙马霍斯", Arrays.asList(3, 1, 2)).build(),
                nameScoresMap);

        final List<Map<String, String>> data = ListKit.of(
                MapKit.builder("name", "sam").put("count", "80").map(),
                MapKit.builder("name", "sam").put("count", "81").map(),
                MapKit.builder("name", "sam").put("count", "82").map(),
                MapKit.builder("name", "jack").put("count", "80").map(),
                MapKit.builder("name", "jack").put("count", "90").map()
        );

        final Map<String, Map<String, List<String>>> nameMap = data.stream()
                .collect(Collectors.groupingBy(e -> e.get("name"), CollectorKit.reduceListMap()));
        Assertions.assertEquals(MapKit.builder("jack", MapKit.builder("name", Arrays.asList("jack", "jack"))
                        .put("count", Arrays.asList("80", "90")).build())
                .put("sam", MapKit.builder("name", Arrays.asList("sam", "sam", "sam"))
                        .put("count", Arrays.asList("80", "81", "82")).build())
                .build(), nameMap);
    }

    @Test
    public void testTransform() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4)
                .collect(CollectorKit.transform(EasyStream::of));
        Assertions.assertEquals(EasyStream.class, stream.getClass());

        stream = Stream.of(1, 2, 3, 4)
                .collect(CollectorKit.transform(HashSet::new, EasyStream::of));
        Assertions.assertEquals(EasyStream.class, stream.getClass());
    }

    @Test
    public void testToEasyStream() {
        final Stream<Integer> stream = Stream.of(1, 2, 3, 4)
                .collect(CollectorKit.toEasyStream());
        Assertions.assertEquals(EasyStream.class, stream.getClass());
    }

    @Test
    public void testToEntryStream() {
        final Map<String, Integer> map = Stream.of(1, 2, 3, 4, 5)
                // 转为EntryStream
                .collect(CollectorKit.toEntryStream(Function.identity(), String::valueOf))
                // 过滤偶数
                .filterByKey(k -> (k & 1) == 1)
                .inverse()
                .toMap();
        Assertions.assertEquals((Integer) 1, map.get("1"));
        Assertions.assertEquals((Integer) 3, map.get("3"));
        Assertions.assertEquals((Integer) 5, map.get("5"));
    }

    @Test
    public void testFiltering() {
        final Map<Integer, Long> map = Stream.of(1, 2, 3)
                .collect(Collectors.groupingBy(Function.identity(),
                        CollectorKit.filtering(i -> i > 1, Collectors.counting())
                ));
        Assertions.assertEquals(MapKit.builder()
                .put(1, 0L)
                .put(2, 1L)
                .put(3, 1L)
                .build(), map);
    }

    @Test
    public void testGroupingByAfterValueMapped() {
        final List<Integer> list = Arrays.asList(1, 1, 2, 2, 3, 4);
        Map<Boolean, Set<String>> map = list.stream()
                .collect(CollectorKit.groupingBy(t -> (t & 1) == 0, String::valueOf, LinkedHashSet::new, LinkedHashMap::new));

        Assertions.assertEquals(LinkedHashMap.class, map.getClass());
        Assertions.assertEquals(new LinkedHashSet<>(Arrays.asList("2", "4")), map.get(Boolean.TRUE));
        Assertions.assertEquals(new LinkedHashSet<>(Arrays.asList("1", "3")), map.get(Boolean.FALSE));

        map = list.stream()
                .collect(CollectorKit.groupingBy(t -> (t & 1) == 0, String::valueOf, LinkedHashSet::new));
        Assertions.assertEquals(HashMap.class, map.getClass());
        Assertions.assertEquals(new LinkedHashSet<>(Arrays.asList("2", "4")), map.get(Boolean.TRUE));
        Assertions.assertEquals(new LinkedHashSet<>(Arrays.asList("1", "3")), map.get(Boolean.FALSE));

        final Map<Boolean, List<String>> map2 = list.stream()
                .collect(CollectorKit.groupingBy(t -> (t & 1) == 0, String::valueOf));
        Assertions.assertEquals(Arrays.asList("2", "2", "4"), map2.get(Boolean.TRUE));
        Assertions.assertEquals(Arrays.asList("1", "1", "3"), map2.get(Boolean.FALSE));

    }

}
