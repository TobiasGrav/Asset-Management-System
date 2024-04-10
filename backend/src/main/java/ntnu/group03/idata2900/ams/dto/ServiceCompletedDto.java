package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.model.AssetOnSite;
import ntnu.group03.idata2900.ams.model.Service;
import ntnu.group03.idata2900.ams.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class ServiceCompletedDto {
    private LocalDateTime timeCompleted;

    private LocalDateTime lastService;

    private AssetOnSite assetOnSite;

    private Service service;

    private User user;

    public ServiceCompletedDto(LocalDateTime timeCompleted, LocalDateTime lastService, AssetOnSite assetOnSite, Service service, User user) {
        this.timeCompleted = timeCompleted;
        this.lastService = lastService;
        this.assetOnSite = assetOnSite;
        this.service = service;
        this.user = user;
    }

    public ServiceCompletedDto(){

    }
}
