/**
 * Copyright (c) Codice Foundation
 * <p>
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. A copy of the GNU Lesser General Public License
 * is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package org.codice.ddf.registry.schemabindings;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.codice.ddf.parser.Parser;
import org.codice.ddf.parser.ParserConfigurator;
import org.codice.ddf.parser.ParserException;
import org.codice.ddf.parser.xml.XmlParser;
import org.junit.Before;
import org.junit.Test;

import oasis.names.tc.ebxml_regrep.xsd.rim._3.AssociationType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.EmailAddressType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.InternationalStringType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.OrganizationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.PersonNameType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.PersonType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.PostalAddressType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryPackageType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ServiceBindingType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.TelephoneNumberType;

public class RegistryPackageUtilsTest {
    private Parser parser;

    private ParserConfigurator configurator;

    @Before
    public void setUp() {
        parser = new XmlParser();

        configurator = parser.configureParser(Arrays.asList(RegistryObjectType.class.getPackage()
                        .getName(),
                RegistryPackageUtils.OGC_FACTORY.getClass()
                        .getPackage()
                        .getName(),
                RegistryPackageUtils.GML_FACTORY.getClass()
                        .getPackage()
                        .getName()),
                this.getClass()
                        .getClassLoader());
    }

    @Test
    public void testGetBindingTypesFromPackage() throws Exception {
        RegistryObjectType registryObjectType = getRegistryObjectFromResource(
                "/csw-registry-package-smaller.xml");

        List<ServiceBindingType> bindings =
                RegistryPackageUtils.getBindingTypes((RegistryPackageType) registryObjectType);

        assertBindings(bindings);
    }

    @Test
    public void testGetBindingTypesFromRegistryObjectList() throws Exception {
        RegistryPackageType registryObjectType =
                (RegistryPackageType) getRegistryObjectFromResource(
                        "/csw-registry-package-smaller.xml");

        RegistryObjectListType registryObjectList = registryObjectType.getRegistryObjectList();

        List<ServiceBindingType> bindings =
                RegistryPackageUtils.getBindingTypes(registryObjectList);

        assertBindings(bindings);
    }

    @Test
    public void testGetBindingTypesFromNull() throws Exception {
        List<ServiceBindingType> bindings =
                RegistryPackageUtils.getBindingTypes((RegistryPackageType) null);
        assertThat(bindings, is(empty()));

        bindings = RegistryPackageUtils.getBindingTypes((RegistryObjectListType) null);
        assertThat(bindings, is(empty()));
    }

    @Test
    public void testGetExtrinsicObjectTypesFromPackage() throws Exception {
        RegistryObjectType registryObjectType = getRegistryObjectFromResource(
                "/csw-registry-package-smaller.xml");

        List<ExtrinsicObjectType> extrinsicObjects =
                RegistryPackageUtils.getExtrinsicObjects((RegistryPackageType) registryObjectType);

        assertExtrinsicObjects(extrinsicObjects);
    }

    @Test
    public void testGetExtrinsicObjectTypesFromRegistryObjectList() throws Exception {
        RegistryPackageType registryObjectType =
                (RegistryPackageType) getRegistryObjectFromResource(
                        "/csw-registry-package-smaller.xml");

        RegistryObjectListType registryObjectList = registryObjectType.getRegistryObjectList();

        List<ExtrinsicObjectType> extrinsicObjects = RegistryPackageUtils.getExtrinsicObjects(
                registryObjectList);

        assertExtrinsicObjects(extrinsicObjects);
    }

    @Test
    public void testGetExtrinsicObjectTypesFromNull() throws Exception {
        List<ExtrinsicObjectType> extrinsicObjects =
                RegistryPackageUtils.getExtrinsicObjects((RegistryPackageType) null);
        assertThat(extrinsicObjects, is(empty()));

        extrinsicObjects = RegistryPackageUtils.getExtrinsicObjects((RegistryObjectListType) null);
        assertThat(extrinsicObjects, is(empty()));
    }

    @Test
    public void testGetOrganizationsFromPackage() throws Exception {
        RegistryObjectType registryObjectType = getRegistryObjectFromResource(
                "/csw-registry-package-smaller.xml");

        List<OrganizationType> organizations =
                RegistryPackageUtils.getOrganizations((RegistryPackageType) registryObjectType);

        assertOrganizations(organizations);
    }

    @Test
    public void testGetOrganizationsFromRegistryObjectList() throws Exception {
        RegistryPackageType registryObjectType =
                (RegistryPackageType) getRegistryObjectFromResource(
                        "/csw-registry-package-smaller.xml");

        RegistryObjectListType registryObjectList = registryObjectType.getRegistryObjectList();

        List<OrganizationType> organizations = RegistryPackageUtils.getOrganizations(
                registryObjectList);

        assertOrganizations(organizations);
    }

    @Test
    public void testGetOrganizationsFromNull() throws Exception {
        List<OrganizationType> organizations =
                RegistryPackageUtils.getOrganizations((RegistryPackageType) null);
        assertThat(organizations, is(empty()));

        organizations = RegistryPackageUtils.getOrganizations((RegistryObjectListType) null);
        assertThat(organizations, is(empty()));
    }

    @Test
    public void testGetPersonsFromPackage() throws Exception {
        RegistryObjectType registryObjectType = getRegistryObjectFromResource(
                "/csw-registry-package-smaller.xml");

        List<PersonType> persons =
                RegistryPackageUtils.getPersons((RegistryPackageType) registryObjectType);

        assertPersons(persons);
    }

    @Test
    public void testGetPersonsFromRegistryObjectList() throws Exception {
        RegistryPackageType registryObjectType =
                (RegistryPackageType) getRegistryObjectFromResource(
                        "/csw-registry-package-smaller.xml");

        RegistryObjectListType registryObjectList = registryObjectType.getRegistryObjectList();

        List<PersonType> persons = RegistryPackageUtils.getPersons(
                registryObjectList);

        assertPersons(persons);
    }

    @Test
    public void testGetPersonsFromNull() throws Exception {
        List<PersonType> persons =
                RegistryPackageUtils.getPersons((RegistryPackageType) null);
        assertThat(persons, is(empty()));

        persons = RegistryPackageUtils.getPersons((RegistryObjectListType) null);
        assertThat(persons, is(empty()));
    }

    @Test
    public void testGetAssociationsFromPackage() throws Exception {
        RegistryObjectType registryObjectType = getRegistryObjectFromResource(
                "/csw-registry-package-smaller.xml");

        List<AssociationType1> associations =
                RegistryPackageUtils.getAssociations((RegistryPackageType) registryObjectType);

        assertAssociations(associations);
    }

    @Test
    public void testGetAssociationsFromRegistryObjectList() throws Exception {
        RegistryPackageType registryObjectType =
                (RegistryPackageType) getRegistryObjectFromResource(
                        "/csw-registry-package-smaller.xml");

        RegistryObjectListType registryObjectList = registryObjectType.getRegistryObjectList();

        List<AssociationType1> associations = RegistryPackageUtils.getAssociations(
                registryObjectList);

        assertAssociations(associations);
    }

    @Test
    public void testGetAssociationsFromNull() throws Exception {
        List<AssociationType1> associations =
                RegistryPackageUtils.getAssociations((RegistryPackageType) null);
        assertThat(associations, is(empty()));

        associations = RegistryPackageUtils.getAssociations((RegistryObjectListType) null);
        assertThat(associations, is(empty()));
    }

    @Test
    public void testGetSlotByName() throws Exception {
        RegistryObjectType registryObjectType = getRegistryObjectFromResource(
                "/csw-registry-package-smaller.xml");

        List<ServiceBindingType> bindings =
                RegistryPackageUtils.getBindingTypes((RegistryPackageType) registryObjectType);

        ServiceBindingType binding = bindings.get(0);
        assertThat(binding.isSetSlot(), is(true));

        Map<String, SlotType1> slotMap = RegistryPackageUtils.getNameSlotMap(binding.getSlot());

        for(String slotName : slotMap.keySet()) {
            SlotType1 slot = slotMap.get(slotName);
            assertThat(slot, is(equalTo(RegistryPackageUtils.getSlotByName(slotName, binding.getSlot()))));
        }
    }

    @Test
    public void testGetSlotFromString() throws Exception {
        RegistryObjectType registryObjectType = getRegistryObjectFromResource(
                "/csw-registry-package-smaller.xml");

        List<ExtrinsicObjectType> bindings =
                RegistryPackageUtils.getExtrinsicObjects((RegistryPackageType) registryObjectType);

        ExtrinsicObjectType binding = bindings.get(0);
        assertThat(binding.isSetSlot(), is(true));

        Map<String, SlotType1> slotMap = RegistryPackageUtils.getNameSlotMap(binding.getSlot());

        for(String slotName : slotMap.keySet()) {
            if (slotName.equals("location")) {
                // RegistryPackageUtils doesn't handle parsing the wrs:ValueList
                continue;
            }
            SlotType1 slot = slotMap.get(slotName);

            List<String> values = RegistryPackageUtils.getSlotStringValues(slot);

            SlotType1 created;
            if (values.size() > 1) {
                created = RegistryPackageUtils.getSlotFromStrings(slot.getName(), values, slot.getSlotType());
            } else {
                created = RegistryPackageUtils.getSlotFromString(slot.getName(), values.get(0), slot.getSlotType());
            }

            assertThat(slot, is(equalTo(created)));
        }

    }

    private void assertBindings(List<ServiceBindingType> bindings) {
        // Values from xml file
        int expectedSize = 1;
        int numberOfSlots = 4;
        String expectedName = "CSW Federation Method";
        String expectedDescription = "This is the CSW federation method.";
        String expectedVersion = "2.0.2";

        String cswUrlSlotName = "cswUrl";
        String cswUrlSlotValue = "https://some/address/here";

        String bindingTypeSlotName = "bindingType";
        String bindingTypeSlotValue = "Csw_Federated_Source";

        String serviceTypeSlotName = "serviceType";
        String serviceTypeSlotValue = "REST";

        String endpointDocumentationSlotName = "endpointDocumentation";
        String endpointDocumentationSlotValue = "https://some/path/to/docs.html";

        assertThat(bindings, hasSize(expectedSize));

        ServiceBindingType binding = bindings.get(0);
        assertThat(binding.isSetAccessURI(), is(false));

        assertThat(binding.isSetName(), is(true));
        assertIst(binding.getName(), expectedName);

        assertThat(binding.isSetDescription(), is(true));
        assertIst(binding.getDescription(), expectedDescription);

        assertThat(binding.isSetVersionInfo(), is(true));
        assertThat(binding.getVersionInfo()
                .getVersionName(), is(equalTo(expectedVersion)));

        assertThat(binding.isSetSlot(), is(true));
        assertThat(binding.getSlot(), hasSize(numberOfSlots));
        Map<String, List<SlotType1>> slotMap = RegistryPackageUtils.getNameSlotMapDuplicateSlotNamesAllowed(binding.getSlot());

        assertThat(slotMap, hasKey(cswUrlSlotName));
        assertSlotValue(slotMap.get(cswUrlSlotName).get(0), 1, cswUrlSlotValue);

        assertThat(slotMap, hasKey(bindingTypeSlotName));
        assertSlotValue(slotMap.get(bindingTypeSlotName).get(0), 1, bindingTypeSlotValue);

        assertThat(slotMap, hasKey(serviceTypeSlotName));
        assertSlotValue(slotMap.get(serviceTypeSlotName).get(0), 1, serviceTypeSlotValue);

        assertThat(slotMap, hasKey(endpointDocumentationSlotName));
        assertSlotValue(slotMap.get(endpointDocumentationSlotName).get(0),
                1,
                endpointDocumentationSlotValue);
    }

    private void assertExtrinsicObjects(List<ExtrinsicObjectType> extrinsicObjects) {
        // Values from xml file
        int expectedSize = 1;
        int numberOfSlots = 6;
        String expectedName = "Node Name";
        String expectedDescription =
                "A little something describing this node in less than 1024 characters";
        String expectedVersion = "2.9.x";

        String linksSlotName = "links";
        String linksSlotValue = "https://some/link/to/my/repo";

        String regionSlotName = "region";
        String regionSlotValue = "USA";

        String inputDataSourcesSlotName = "inputDataSources";
        String inputDataSourcesSlotValue1 = "youtube";
        String inputDataSourcesSlotValue2 = "myCamera";

        String dataTypesSlotName = "dataTypes";
        String dataTypesSlotValue1 = "video";
        String dataTypesSlotValue2 = "sensor";

        String securityLevelSlotName = "securityLevel";
        String securityLevelSlotValue = "role=guest";

        assertThat(extrinsicObjects, hasSize(expectedSize));

        ExtrinsicObjectType extrinsicObject = extrinsicObjects.get(0);

        assertThat(extrinsicObject.isSetName(), is(true));
        assertIst(extrinsicObject.getName(), expectedName);

        assertThat(extrinsicObject.isSetDescription(), is(true));
        assertIst(extrinsicObject.getDescription(), expectedDescription);

        assertThat(extrinsicObject.isSetVersionInfo(), is(true));
        assertThat(extrinsicObject.getVersionInfo()
                .getVersionName(), is(equalTo(expectedVersion)));

        assertThat(extrinsicObject.isSetSlot(), is(true));
        assertThat(extrinsicObject.getSlot(), hasSize(numberOfSlots));
        Map<String, SlotType1> slotMap =
                RegistryPackageUtils.getNameSlotMap(extrinsicObject.getSlot());

        assertThat(slotMap, hasKey(linksSlotName));
        assertSlotValue(slotMap.get(linksSlotName), 1, linksSlotValue);

        assertThat(slotMap, hasKey(regionSlotName));
        assertSlotValue(slotMap.get(regionSlotName), 1, regionSlotValue);

        assertThat(slotMap, hasKey(securityLevelSlotName));
        assertSlotValue(slotMap.get(securityLevelSlotName), 1, securityLevelSlotValue);

        assertThat(slotMap, hasKey(inputDataSourcesSlotName));
        assertSlotValue(slotMap.get(inputDataSourcesSlotName),
                2,
                inputDataSourcesSlotValue1,
                inputDataSourcesSlotValue2);

        assertThat(slotMap, hasKey(dataTypesSlotName));
        assertSlotValue(slotMap.get(dataTypesSlotName),
                2,
                dataTypesSlotValue1,
                dataTypesSlotValue2);
    }

    private void assertOrganizations(List<OrganizationType> organizations) {
        // Values from xml file
        int expectedSize = 1;
        String expectedName = "Codice";
        String expectedCity = "Phoenix";
        String expectedCountry = "USA";
        String expectedPostalCode = "85037";
        String expectedState = "AZ";
        String expectedStreet = "1234 Some Street";

        String expectedAreaCode = "555";
        String expectedNumber = "555-5555";
        String expectedExtension = "1234";
        String expectedCountryCode = null;
        String expectedPhoneType = null;

        String expectedEmail = "emailaddress@something.com";

        String expectedParent = "urn:uuid:2014ca7f59ac46f495e32b4a67a51276";
        String expectedPrimaryContact = "somePrimaryContact";

        assertThat(organizations, hasSize(expectedSize));
        OrganizationType organization = organizations.get(0);

        assertThat(organization.isSetName(), is(true));
        assertIst(organization.getName(), expectedName);

        assertThat(organization.isSetAddress(), is(true));
        List<PostalAddressType> addresses = organization.getAddress();
        assertThat(addresses, hasSize(expectedSize));
        assertAddress(addresses.get(0),
                expectedCity,
                expectedCountry,
                expectedPostalCode,
                expectedState,
                expectedStreet);

        assertThat(organization.isSetTelephoneNumber(), is(true));
        List<TelephoneNumberType> phoneNumbers = organization.getTelephoneNumber();
        assertThat(phoneNumbers, hasSize(expectedSize));
        assertPhoneNumbers(phoneNumbers.get(0),
                expectedAreaCode,
                expectedNumber,
                expectedExtension, expectedCountryCode, expectedPhoneType);

        assertThat(organization.isSetEmailAddress(), is(true));
        assertThat(organization.getEmailAddress(), hasSize(expectedSize));
        List<EmailAddressType> emailAddresses = organization.getEmailAddress();
        assertThat(emailAddresses.get(0)
                .getAddress(), is(equalTo(expectedEmail)));

        assertThat(organization.isSetParent(), is(true));
        assertThat(organization.getParent(), is(equalTo(expectedParent)));

        assertThat(organization.isSetPrimaryContact(), is(true));
        assertThat(organization.getPrimaryContact(), is(equalTo(expectedPrimaryContact)));
    }

    private void assertPersons(List<PersonType> persons) {
        // Values from xml file
        int expectedSize = 1;
        String expectedFirstName = "john";
        String expectedMiddleName = "middleName";
        String expectedLastname = "doe";

        String expectedCity = "Phoenix";
        String expectedCountry = "USA";
        String expectedPostalCode = "85037";
        String expectedState = "AZ";
        String expectedStreet = "1234 Some Street";

        String expectedAreaCode = "111";
        String expectedNumber = "111-1111";
        String expectedExtension = "1234";
        String expectedCountryCode = "country";
        String expectedPhoneType = "cell phone";

        String expectedEmail = "emailaddress@something.com";

        assertThat(persons, hasSize(expectedSize));
        PersonType person = persons.get(0);

        assertThat(person.isSetPersonName(), is(true));
        assertPersonName(person.getPersonName(), expectedFirstName, expectedMiddleName, expectedLastname);

        assertThat(person.isSetAddress(), is(true));
        List<PostalAddressType> addresses = person.getAddress();
        assertThat(addresses, hasSize(expectedSize));
        assertAddress(addresses.get(0),
                expectedCity,
                expectedCountry,
                expectedPostalCode,
                expectedState,
                expectedStreet);

        assertThat(person.isSetTelephoneNumber(), is(true));
        List<TelephoneNumberType> phoneNumbers = person.getTelephoneNumber();
        assertThat(phoneNumbers, hasSize(expectedSize));
        assertPhoneNumbers(phoneNumbers.get(0),
                expectedAreaCode,
                expectedNumber,
                expectedExtension, expectedCountryCode, expectedPhoneType);

        assertThat(person.isSetEmailAddress(), is(true));
        assertThat(person.getEmailAddress(), hasSize(expectedSize));
        List<EmailAddressType> emailAddresses = person.getEmailAddress();
        assertThat(emailAddresses.get(0)
                .getAddress(), is(equalTo(expectedEmail)));
    }

    private void assertAssociations(List<AssociationType1> associations) {
        // Values from xml file
        int expectedSize = 1;
        String expectedId = "urn:assoication:1";
        String expectedAssociationType = "RelatedTo";
        String expectedSourceObject = "urn:registry:node";
        String expectedTargetObject = "urn:contact:id0";

        assertThat(associations, hasSize(expectedSize));

        AssociationType1 association = associations.get(0);
        assertThat(association.isSetId(), is(true));
        assertThat(association.getId(), is(equalTo(expectedId)));

        assertThat(association.isSetAssociationType(), is(true));
        assertThat(association.getAssociationType(), is(equalTo(expectedAssociationType)));

        assertThat(association.isSetSourceObject(), is(true));
        assertThat(association.getSourceObject(), is(equalTo(expectedSourceObject)));

        assertThat(association.isSetTargetObject(), is(true));
        assertThat(association.getTargetObject(), is(equalTo(expectedTargetObject)));
    }


    private void assertIst(InternationalStringType actual, String expectedName) {
        assertThat(RegistryPackageUtils.getStringFromIST(actual), is(equalTo(expectedName)));
    }

    private void assertSlotValue(SlotType1 slot, int size, String... expectedValues) {
        List<String> actualValues = RegistryPackageUtils.getSlotStringValues(slot);
        assertThat(actualValues, hasSize(size));

        assertThat(actualValues, contains(expectedValues));
    }

    private void assertAddress(PostalAddressType address, String city, String country, String zip,
            String state, String street) {
        assertThat(address.getCity(), is(equalTo(city)));
        assertThat(address.getCountry(), is(equalTo(country)));
        assertThat(address.getPostalCode(), is(equalTo(zip)));
        assertThat(address.getStateOrProvince(), is(equalTo(state)));
        assertThat(address.getStreet(), is(equalTo(street)));
    }

    private void assertPhoneNumbers(TelephoneNumberType phoneNumber, String areaCode, String number,
            String extension, String countryCode, String phoneType) {
        assertThat(phoneNumber.getAreaCode(), is(equalTo(areaCode)));
        assertThat(phoneNumber.getNumber(), is(equalTo(number)));
        assertThat(phoneNumber.getExtension(), is(equalTo(extension)));
        assertThat(phoneNumber.getCountryCode(), is(equalTo(countryCode)));
        assertThat(phoneNumber.getPhoneType(), is(equalTo(phoneType)));

    }

    private void assertPersonName(PersonNameType name, String first, String middle, String last) {
        assertThat(name.getFirstName(), is(equalTo(first)));
        assertThat(name.getMiddleName(), is(equalTo(middle)));
        assertThat(name.getLastName(), is(equalTo(last)));
    }

    private RegistryObjectType getRegistryObjectFromResource(String path) throws ParserException {
        RegistryObjectType registryObject = null;
        JAXBElement<RegistryObjectType> jaxbRegistryObject = parser.unmarshal(configurator,
                JAXBElement.class,
                getClass().getResourceAsStream(path));

        if (jaxbRegistryObject != null) {
            registryObject = jaxbRegistryObject.getValue();
        }

        return registryObject;
    }
}