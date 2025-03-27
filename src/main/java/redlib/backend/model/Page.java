package redlib.backend.model;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class Page<T> {
    private int current;
    private int pageSize;
    private int total;
    private List<T> list;

    public Page() {

    }

    public Page(int page, int pageSize, int total, List<T> dtoList) {
        this.current = page;
        this.pageSize = pageSize;
        this.total = total;
        this.setList(dtoList);
    }

    public static Page getNullPage(Integer page, Integer pageSize) {
        return new Page(page, pageSize, 0, Collections.emptyList());
    }
}