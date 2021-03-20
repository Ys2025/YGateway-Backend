package cn.yanghuisen.gateway.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Y
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableResult<T> {
    private Long total = 0L;
    private List<T> data;

    public TableResult(List<T> data) {
        this.data = data;
        this.total = Long.parseLong(String.valueOf(data.size()));
    }

    public TableResult(List<T> data,Long total) {
        this.data = data;
        this.total = total;
    }
}
