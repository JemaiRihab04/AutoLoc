package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(target = "id", source = "idVehicule")
    VehiculeDTO toDto(Vehicule vehicule);

    @Mapping(target = "idVehicule", source = "id")
    Vehicule toEntity(VehiculeDTO dto);
}