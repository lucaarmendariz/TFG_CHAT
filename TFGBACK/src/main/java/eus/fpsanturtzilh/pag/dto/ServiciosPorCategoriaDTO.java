package eus.fpsanturtzilh.pag.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiciosPorCategoriaDTO {
    private long trabajador_id;
    private String trabajador;
    private String categoria;
    private Long cantidad;
}
