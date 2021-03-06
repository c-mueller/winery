/*******************************************************************************
 * Copyright (c) 2015-2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v20.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Sebastian Wagner - initial API and implementation
 *******************************************************************************/
package org.eclipse.winery.bpmn2bpel.model;

import org.jgrapht.graph.DefaultEdge;

public class Link extends DefaultEdge {

	public Node getSource() {
		return (Node) super.getSource();
	}

	public Node getTarget() {
		return (Node) super.getTarget();
	}

}
