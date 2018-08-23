package com.hertz.digital.platform.exporter.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.bean.RedemptionData;
import com.hertz.digital.platform.bean.Reward;
import com.hertz.digital.platform.bean.RewardType;
import com.hertz.digital.platform.bean.RewardsDatum;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;

/**
 * The component specific implementation of the exporter service required to
 * return the specific requested bean object.
 * 
 * Notice the identifier parameter which must be kept unique for each of the
 * created services.
 * 
 * @author n.kumar.singhal
 *
 */
@Component(immediate = true, metatype = true,
        label = "Hertz - Redemption Table D Exporter Service",
        description = "Hertz - Exposes the Redemption Table D component as bean/json")
@Service(value = ComponentExporterService.class)
@Properties(value = { @Property(name = "identifier",
        value = "goldplusrewards/redemption:tablecontainer_d") })
public class RedemptionTableContainerDExporterImpl
        implements ComponentExporterService {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4228964176731861904L;

    /** The Constant resourceType. */
    private static final String RESOURCE_TYPE = "hertz/components/content/tablecontainer_d";
    
    /** The Constant for table container d. */
    private static final String TABLECONTAINER_D = "tablecontainer_d";

    /**
     * Default Constructor Declaration.
     */
    public RedemptionTableContainerDExporterImpl() {
        super();
    }

    /** LOGGER instantiation. */
    private static final Logger logger = LoggerFactory
            .getLogger(RedemptionTableContainerDExporterImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.exporter.api.ComponentExporterService#
     * exportAsJson(org.apache.sling.api.resource.Resource,
     * org.apache.sling.api.resource.ResourceResolver)
     */
    @Override
    public String exportAsJson(Resource component, ResourceResolver resolver) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.exporter.api.ComponentExporterService#
     * exportAsBean(org.apache.sling.api.resource.Resource,
     * org.apache.sling.api.resource.ResourceResolver)
     */
    @Override
    public Object exportAsBean(Resource component, ResourceResolver resolver) {
        logger.debug("Start:: exportAsBean()");
        RedemptionData redemptionData = new RedemptionData();
        List<RewardType> rewardsTypeList = new ArrayList<>();
        if (null != component
                && null != component.getChild(TABLECONTAINER_D)) {

            redemptionData.setCountryName((String) component.getParent()
                    .getValueMap().get("jcr:title"));

            String[] columnname = (String[]) component
                    .getChild(TABLECONTAINER_D).getValueMap()
                    .get("columnname");
            if (columnname.length > 1) {
                for (int i = 1; i < columnname.length; i++) {
                    RewardType rewardType = new RewardType();
                    rewardType.setRewardType(columnname[i]);
                    rewardType.setRewardDescription(columnname[i]);
                    rewardType.setRewards(getRewardsList(component, i));
                    rewardsTypeList.add(rewardType);
                }
            }
            redemptionData.setRewardTypes(rewardsTypeList);

        }
        logger.debug("Ends:: exportAsBean()");
        return redemptionData;
    }

    /**
     * This will logically pick the rewards data from the column 0 and the
     * passed index and build the rewards object.
     *
     * @param component
     *            the component
     * @param associatedIndex
     *            the associated index
     * @return the rewards list
     */
    private List<Reward> getRewardsList(Resource component,
            int associatedIndex) {
        List<Reward> rewardsList = new ArrayList<>();

        Iterator<Resource> iterator = component.listChildren();
        while (iterator.hasNext()) {
            Resource child = iterator.next();
            if (RESOURCE_TYPE.equals(child.getResourceType())) {
                Reward reward = new Reward();
                List<RewardsDatum> rewardsDataList = new ArrayList<>();
                reward.setTitle(
                        ((String[]) child.getValueMap().get("columnname"))[0]);
                // tablePar (only one per container)
                if (child.hasChildren()) {
                	setChildResourceValues(child, associatedIndex, rewardsDataList); 
                }
                reward.setRewardsData(rewardsDataList);
                rewardsList.add(reward);
            }
        }
        return rewardsList;
    }
    
    /**
     * This method will iterate through the rows and set values
     * @param child
     * @param associatedIndex
     * @param rewardsDataList
     */
    private void setChildResourceValues(Resource child, int associatedIndex, List<RewardsDatum> rewardsDataList){
    	Iterator<Resource> rowsIterator = child.getChild("tablePar")
                .listChildren();
        while (rowsIterator.hasNext()) {
            RewardsDatum rewardsData = new RewardsDatum();
            Resource interimResource = rowsIterator.next();
            if (interimResource.hasChildren()) {
                Resource tableRowsResource = interimResource
                        .getChild("rows");
                ValueMap rowsValueMap = tableRowsResource
                        .getValueMap();
                rewardsData.setTitle(StringUtils.deleteWhitespace(
                        (String) rowsValueMap.get("column0")));
                rewardsData.setDescription(
                        (String) rowsValueMap.get("subcolumn0"));
                rewardsData.setPoints(
                        StringUtils.trim((String) rowsValueMap
                                .get("column" + associatedIndex)));
                rewardsData.setPointsText((String) rowsValueMap
                        .get("subcolumn" + associatedIndex));
            }
            rewardsDataList.add(rewardsData);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hertz.digital.platform.exporter.api.ComponentExporterService#
     * exportAsMap(org.apache.sling.api.resource.ResourceResolver,
     * org.apache.sling.api.resource.Resource)
     */
    @Override
    public Object exportAsMap(ResourceResolver resolver,
            Resource configPageResource)
            throws JSONException, RepositoryException {
        return null;
    }

}
