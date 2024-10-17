package com.rule.engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class MetaDataModel {
    String uploadedBy;
    String updatedBy;
    Date createdAt;
    Date updatedAt;

    public MetaDataModel(MetaDataModel metaDataModel){
        this.uploadedBy = metaDataModel.getUploadedBy();
        this.updatedBy = metaDataModel.getUpdatedBy();
        this.createdAt = metaDataModel.getCreatedAt();
        this.updatedAt = metaDataModel.getUpdatedAt();
    }
}
