/*******************************************************************************
 * Copyright (c) 2014-2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v20.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Oliver Kopp - initial API and implementation
 *     Tino Stadelmaier - JSON implementation
 *******************************************************************************/
package org.eclipse.winery.repository.rest.resources.servicetemplates.boundarydefinitions.reqscaps;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.winery.model.tosca.TRequirement;
import org.eclipse.winery.model.tosca.TRequirementRef;
import org.eclipse.winery.model.tosca.utils.ModelUtilities;
import org.eclipse.winery.repository.rest.resources._support.IPersistable;
import org.eclipse.winery.repository.rest.resources._support.collections.CollectionsHelper;
import org.eclipse.winery.repository.rest.resources._support.collections.withoutid.EntityWithoutIdCollectionResource;
import org.eclipse.winery.repository.rest.resources.apiData.RequirementsOrCapabilityApiData;
import org.eclipse.winery.repository.rest.resources.servicetemplates.ServiceTemplateResource;

/**
 * This class is mirrored at {@link org.eclipse.winery.repository.resources.servicetemplates.boundarydefinitions.reqscaps.CapabilitiesResource}
 */
public class RequirementsResource extends EntityWithoutIdCollectionResource<RequirementResource, TRequirementRef> {

	public RequirementsResource(IPersistable res, List<TRequirementRef> refs) {
		super(RequirementResource.class, TRequirementRef.class, refs, res);
	}

	/**
	 * Adds an element using form-encoding
	 *
	 * This is necessary as TRequirementRef contains an IDREF and the XML snippet itself does not contain the target id
	 *
	 * @param name      the optional name of the requirement
	 * @param reference the reference to a requirement in the topology
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addNewElement(@FormParam("name") String name, @FormParam("ref") String reference) {
		// Implementation adapted from super addNewElement

		if (reference == null) {
			return Response.status(Status.BAD_REQUEST).entity("A reference has to be provided").build();
		}

		TRequirementRef ref = new TRequirementRef();
		ref.setName(name); // may also be null

		// The XML model forces us to put a reference to the object and not just the string
		ServiceTemplateResource rs = (ServiceTemplateResource) this.res;
		TRequirement resolved = ModelUtilities.resolveRequirement(rs.getServiceTemplate(), reference);
		// In case nothing was found: report back to the user
		if (resolved == null) {
			return Response.status(Status.BAD_REQUEST).entity("Reference could not be resolved").build();
		}

		ref.setRef(resolved);

		// "this.alreadyContains(ref)" cannot be called as this leads to a mappable exception: The data does not contain an id where the given ref attribute may point to

		this.list.add(ref);
		return CollectionsHelper.persist(this.res, this, ref, true);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewElementJSON(RequirementsOrCapabilityApiData reqOrCap) {
		// Implementation adapted from super addNewElement

		if (reqOrCap.ref == null) {
			return Response.status(Status.BAD_REQUEST).entity("A reference has to be provided").build();
		}

		TRequirementRef ref = new TRequirementRef();
		ref.setName(reqOrCap.name); // may also be null

		// The XML model forces us to put a reference to the object and not just the string
		ServiceTemplateResource rs = (ServiceTemplateResource) this.res;
		TRequirement resolved = ModelUtilities.resolveRequirement(rs.getServiceTemplate(), reqOrCap.ref);
		// In case nothing was found: report back to the user
		if (resolved == null) {
			return Response.status(Status.BAD_REQUEST).entity("Reference could not be resolved").build();
		}

		ref.setRef(resolved);

		// "this.alreadyContains(ref)" cannot be called as this leads to a mappable exception: The data does not contain an id where the given ref attribute may point to

		this.list.add(ref);
		return CollectionsHelper.persist(this.res, this, ref, true);
	}

	@Override
	@Path("{id}/")
	public RequirementResource getEntityResource(@PathParam("id") String id) {
		return this.getEntityResourceFromEncodedId(id);
	}
}
