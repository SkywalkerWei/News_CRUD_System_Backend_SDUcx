package redlib.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class AdminModDTO {
    private String id;
    private List<String> privList;
}
