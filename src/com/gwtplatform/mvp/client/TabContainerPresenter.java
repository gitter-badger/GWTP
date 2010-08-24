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

package com.gwtplatform.mvp.client;

import com.google.gwt.event.shared.GwtEvent.Type;

import com.gwtplatform.mvp.client.proxy.TabContentProxy;

/**
 * A presenter that can display many tabs and the content of one of these tabs.
 *
 * @param <V> The specific type of the {@link View}. Must implement
 *          {@link TabPanel}.
 * @param <Proxy_> The specific type of the proxy, must be a
 *          {@link TabContainerProxy}.
 * 
 * @author Philippe Beaudoin
 * @author Christian Goudreau
 */
public abstract class TabContainerPresenter<V extends View & TabPanel, Proxy_ extends TabContentProxy<?>> extends Presenter<V, Proxy_> {
  private final Type<RequestTabsHandler> requestTabsEventType;
  private final Object tabContentSlot;
  
  /**
   * Create a presenter that can display many tabs and the content of one of
   * these tabs.
   * 
   * @param eventBus The {@link EventBus}.
   * @param view The {@link View}.
   * @param proxy The proxy, a {@link TabContainerProxy}.
   * @param tabContentSlot An opaque object identifying the slot in which the
   *          main content should be displayed.
   * @param requestTabsEventType The {@link Type} of the object to fire to
   *          identify all the displayed tabs.
   */
  public TabContainerPresenter(EventBus eventBus, V view, Proxy_ proxy,
      Object tabContentSlot, Type<RequestTabsHandler> requestTabsEventType) {
    super(eventBus, view, proxy);
    this.tabContentSlot = tabContentSlot;
    this.requestTabsEventType = requestTabsEventType;
  }

  /**
   * Adds a new tab to this presenter.
   * 
   * @param tabProxy The {@link TabContentProxy} containing information on the
   *          tab to add.
   * @return The newly added tab.
   */
  public Tab addTab(final TabContentProxy<?> tabProxy) {
    return getView().addTab(tabProxy.getLabel(), tabProxy.getHistoryToken(),
        tabProxy.getPriority());
  }
  
  /**
   * This method sets some content in a specific slot of the {@link Presenter}.
   * A {@link ResetPresentersEvent} will be fired after the top-most visible
   * presenter is revealed.
   * 
   * @param slot An opaque object identifying which slot this content is being
   *          set into. The attached view should know what to do with this slot.
   * @param content The content, a {@link TabContainerPresenter}. Passing {@code null}
   *          will clear the slot.
   */
  public void setInSlot(Object slot, TabContainerPresenter<?, ?> content) {
    super.setInSlot(slot, content);
    
    if (slot == tabContentSlot) {
      Tab tab = content.getProxy().getTab();
      getView().setActiveTab(tab);
    }
  }
  
  @Override
  protected void onBind() {
    super.onBind();

    // The following call will trigger a series of call to addTab, so
    // we should make sure we clear all the tabs when unbinding.
    RequestTabsEvent.fire(this, requestTabsEventType, this);
  }

  @Override
  protected void onUnbind() {
    super.onUnbind();

    // The tabs are added indirectly in onBind() via the RequestTabsEvent, so we
    // clear them now.
    getView().removeTabs();
  }
}