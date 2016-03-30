/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata;

import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanActivity;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanDatasetRelationship;
import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanOrganization;
import eu.trentorise.opendata.jackan.model.CkanPair;
import eu.trentorise.opendata.jackan.model.CkanResource;
import eu.trentorise.opendata.jackan.model.CkanState;
import eu.trentorise.opendata.jackan.model.CkanTag;
import eu.trentorise.opendata.jackan.model.CkanTrackingSummary;
import eu.trentorise.opendata.jackan.model.CkanUser;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class main
{
    public static void main(String[] args){
        String url = "http://dados.gov.br/";
        CkanClient cc = new CkanClient(url);
        List<String> datasetlist = cc.getDatasetList();
        for(String datasetname : datasetlist){
            CkanDataset dataset = cc.getDataset(datasetname);
            
            //DATASET
            dataset.getId(); //String
            dataset.getAuthor(); //String
            dataset.getAuthorEmail(); //String
            dataset.getCreatorUserId(); //String
            dataset.getExtras(); //List<CkanPair>
            //dataset.getExtrasAsHashMap(); //Map<String, String>
            dataset.getGroups(); //List<CkanGroup>
            dataset.getLicenseId(); //String
            dataset.getLicenseTitle(); //String
            dataset.getLicenseUrl(); //String
            dataset.getMaintainer(); //String
            dataset.getMaintainerEmail(); //String
            dataset.getMetadataCreated(); //Timestamp
            dataset.getMetadataModified(); //Timestamp
            dataset.getName(); //String
            dataset.getNotes(); //String
            dataset.getNotesRendered(); //String
            dataset.getNumResources(); //int
            dataset.getNumTags(); //int
            dataset.getOrganization(); //CkanOrganization
            dataset.getOthers(); //Map<String, Object>
            dataset.getOwnerOrg(); //String
            dataset.getRelationshipsAsObject(); //List<CkanDatasetRelationship>
            dataset.getRelationshipsAsSubject(); //List<CkanDatasetRelationship>
            dataset.getResources(); //List<CkanResource>
            dataset.getRevisionId(); //String
            dataset.getRevisionTimestamp(); //Timestamp
            dataset.getState(); //CkanState (enum)
            dataset.getTags(); //List<CkanTag>
            dataset.getTitle(); //String
            dataset.getTrackingSummary(); //CkanTrackingSummary
            dataset.getType(); //String
            dataset.getUrl(); //String
            dataset.getVersion(); //String
            dataset.isOpen(); //boolean
            dataset.isPriv(); //boolean
            
            //DATASET RELATIONSHIP
            CkanDatasetRelationship datasetrelationship = dataset.getRelationshipsAsObject().get(0);
            datasetrelationship.getId(); //String
            datasetrelationship.getComment(); //String
            datasetrelationship.getObject(); //String
            datasetrelationship.getSubject(); //String
            datasetrelationship.getType(); //String
            
            //EXTRAS 
            CkanPair extras = dataset.getExtras().get(0);
            extras.getKey(); //String
            extras.getValue(); //String
            
            //GROUP
            CkanGroup group = dataset.getGroups().get(0);
            group.getId(); //String
            group.getApprovalStatus(); //String
            group.getCreated(); //Timestamp
            group.getDescription(); //String
            group.getDisplayName(); //String
            group.getExtras(); //List<CkanPair>
            group.getGroups(); //List<CkanGroup>
            group.getImageDisplayUrl(); //String
            group.getImageUrl(); //String
            group.getName(); //String
            group.getNumFollowers(); //int
            group.getPackageCount(); //int
            group.getPackages(); //List<CkanDataset>
            group.getRevisionId(); //String
            group.getState(); //CkanState
            group.getTitle(); //String
            group.getType(); //String
            group.getUsers(); //List<CkanUser>
            group.isOrganization(); //boolean
            group.nameOrId(); //String
            
            //ORGANIZATION
            CkanOrganization org = dataset.getOrganization();
            org.getId(); //String
            org.getApprovalStatus(); //String
            org.getCreated(); //Timestamp
            org.getDescription(); //String
            org.getDisplayName(); //String
            org.getExtras(); //List<CkanPair>
            org.getGroups(); //List<CkanGroup>
            org.getImageDisplayUrl(); //String
            org.getImageUrl(); //String
            org.getName(); //String
            org.getNumFollowers(); //int
            org.getPackageCount(); //int
            org.getPackages(); //List<Dataset>
            org.getRevisionId(); //String
            org.getState(); //CkanState
            org.getTitle(); //String
            org.getType(); //String
            org.getUsers(); //List<CkanUser>
            org.isOrganization(); //boolean
            org.nameOrId(); //String
            
            //RESOURCE
            CkanResource resource = dataset.getResources().get(0);
            resource.getId(); //String
            resource.getCacheLastUpdated(); //String
            resource.getCacheUrl(); //String
            resource.getCreated(); //Timestamp
            resource.getDescription(); //String
            resource.getFormat(); //String
            resource.getHash(); //String
            resource.getLastModified(); //String
            resource.getMimetype(); //String
            resource.getMimetypeInner(); //String
            resource.getName(); //String
            resource.getOthers(); //Map<String, Object>
            resource.getOwner(); //String
            resource.getPackageId(); //String
            resource.getPosition(); //int
            resource.getResourceGroupId(); //String
            resource.getResourceType(); //String
            resource.getRevisionId(); //String
            resource.getRevisionTimestamp(); //Timestamp
            resource.getSize(); //String
            resource.getState(); //CkanState
            resource.getTrackingSummary(); //CkanTrackingSummary
            resource.getUrl(); //String
            resource.getUrlType(); //String
            resource.getWebstoreLastUpdated(); //Timestamp
            resource.getWebstoreUrl(); //String
            
            //STATE
            CkanState state;
            //String enum "active" ou "deleted"
            
            //TAG
            CkanTag tag = dataset.getTags().get(0);
            tag.getId(); //String
            tag.getDisplayName(); //String
            tag.getName(); //String
            tag.getRevisionTimestamp(); //Timestamp
            tag.getState(); //String enum "active" ou "deleted"
            tag.getVocabularyId(); //String
            
            
            //TRACKING SUMMARY
            CkanTrackingSummary summary = dataset.getTrackingSummary();
            summary.getRecent(); //int
            summary.getTotal(); //int
            
            //USER
            CkanUser user = group.getUsers().get(0);
            user.getId(); //String
            user.getAbout(); //String
            user.getActivity(); //List<CkanActivity>
            user.getCapacity(); //String
            user.getCreated(); //Timestamp
            user.getDisplayName(); //Sring
            user.getEmail(); //String
            user.getEmailHash(); //String
            user.getFullname(); //String
            user.getName(); //String
            user.getNumFollowers(); //Integer
            user.getNumberAdministeredPackages(); //int
            user.getNumberOfEdits(); //int
            user.getOpenid(); //String
            user.getPassword(); //String
            user.getState(); //CkanState
            user.isActivityStreamsEmailNotifications(); //boolean
            user.isSysadmin(); //boolean
            
            //ACTIVITY
            CkanActivity activity = user.getActivity().get(0);
            activity.getId(); //String
            activity.getApprovedTimestamp(); //Timestamp
            activity.getAuthor(); //String
            activity.getGroups(); //List<String>
            activity.getMessage(); //String
            activity.getPackages(); //List<Dataset>
            activity.getState(); //CkanState (enum)
            activity.getTimestamp(); //Timestamp
            
        }
        
        
    }
}
