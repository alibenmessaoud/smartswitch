/*Copyright (C) 2017 Roland Hauser, <sourcepond@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package ch.sourcepond.commons.smartswitch.lib;

import org.apache.felix.dm.DependencyActivatorBase;
import org.osgi.framework.BundleContext;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 *
 */
public abstract class SmartSwitchActivatorBase extends DependencyActivatorBase {
    private volatile ExecutorService executorService;

    @Override
    public void start(final BundleContext context) throws Exception {
        executorService = newCachedThreadPool();
        super.start(context);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        super.stop(context);
        executorService.shutdown();
    }

    protected <T> SmartSwitchBuilder<T> createSmartSwitchBuilder(final Class<T> pServiceInterface) {
        return new SmartSwitchBuilderImpl<T>(executorService, new SmartSwitchFactory(executorService), this, pServiceInterface);
    }
}