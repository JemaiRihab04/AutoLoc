package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(target = "id", source = "idMaintenance")
    @Mapping(target = "vehiculeId", source = "vehicule.idVehicule")
    MaintenanceDTO toDto(Maintenance maintenance);

    @Mapping(target = "idMaintenance", source = "id")
    @Mapping(target = "vehicule.idVehicule", source = "vehiculeId")
    Maintenance toEntity(MaintenanceDTO dto);
}