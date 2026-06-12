package io.github.yiyongbo.scaffold.common.util;

import java.util.List;
import java.util.function.Consumer;

/**
 * 批处理工具
 *
 * @author kidd
 * @since 2026/6/12 16:50
 */
public final class BatchUtils {

    private static final int DEFAULT_BATCH_SIZE = 500;

    private BatchUtils() {
    }

    public static <T> void execute(List<T> records, Consumer<List<T>> consumer) {
        execute(records, DEFAULT_BATCH_SIZE, consumer);
    }

    public static <T> void execute(List<T> records, int batchSize, Consumer<List<T>> consumer) {
        if (records == null || records.isEmpty()) {
            return;
        }

        for (int i = 0; i < records.size(); i += batchSize) {
            int end = Math.min(i + batchSize, records.size());
            consumer.accept(records.subList(i, end));
        }
    }

}
