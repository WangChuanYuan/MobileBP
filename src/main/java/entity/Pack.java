package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pack {

    private long pid;

    private String name;

    private double fee;

    private List<Plan> plans;
}
