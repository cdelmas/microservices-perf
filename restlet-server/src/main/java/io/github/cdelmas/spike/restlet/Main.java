/*
   Copyright 2015 Cyril Delmas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.cdelmas.spike.restlet;

import java.util.List;

import io.github.cdelmas.spike.restlet.hello.HelloResource;
import io.github.cdelmas.spike.restlet.infra.JacksonCustomConverter;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;

public class Main {

    public static void main(String[] args) throws Exception {
        replaceConverter(JacksonConverter.class, new JacksonCustomConverter());
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8082);
        component.getDefaultHost().attach("/restlet/hello", HelloResource.class);
        component.start();
    }

    private static void replaceConverter(
            Class<? extends ConverterHelper> converterClass,
            ConverterHelper newConverter) {

        List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
        for (ConverterHelper converter : converters) {
            if (converter.getClass().equals(converterClass)) {
                converters.remove(converter);
                break;
            }
        }

        converters.add(newConverter);
    }
}
