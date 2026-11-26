package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.web.dto.ContratDTO;

@Mapper(componentModel = "spring")
public interface ContratMapper {

    @Mapping(target = "id", source = "idContrat")
    ContratDTO toDto(Contrat contrat);

    @Mapping(target = "idContrat", source = "id")
    Contrat toEntity(ContratDTO dto);
}