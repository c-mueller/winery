/*******************************************************************************
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Christoph Kleine - initial API and implementation
 *******************************************************************************/
package org.eclipse.winery.yaml.common.validator.support;

import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;

public class Parameter extends AbstractParameter<Parameter> {

    @Override
    public Parameter copy() {
        Parameter parameter = new Parameter();
        parameter.getContext().addAll(this.getContext());
        return parameter;
    }

    @Override
    public Parameter self() {
        return this;
    }
}
