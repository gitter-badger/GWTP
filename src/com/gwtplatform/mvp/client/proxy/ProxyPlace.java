/**
 * Copyright 2010 ArcBees Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.mvp.client.proxy;

import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasEventBus;
import com.gwtplatform.mvp.client.Presenter;

/**
 * The interface of a {@link Proxy} that is also a {@link Place}.
 *
 * @param <P> The Presenter's type.
 * 
 * @author Philippe Beaudoin
 */
public interface ProxyPlace<P extends Presenter<?, ?>> extends Proxy<P>, Place,
    HasEventBus {

  /**
   * Makes it possible to access the {@link EventBus} object associated with
   * that presenter.
   * 
   * @return The EventBus associated with that presenter.
   */
  EventBus getEventBus();
}