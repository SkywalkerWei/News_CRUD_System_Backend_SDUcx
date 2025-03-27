package redlib.backend.dto.query;

import lombok.Data;

@Data
public class PageQueryDTO {
    private int current;
    private int pageNum;
    private int pageSize;
    private int offset;
    private int limit=10;
}
