package com.manfentang.admin.config;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * @author yanglf
 * @sine 2018.12.15
 * @descriptipon
 * @see
 */
@Configuration
public class NotifierConfiguration  extends AbstractStatusChangeNotifier {


    public NotifierConfiguration(InstanceRepository repositpry) {
        super(repositpry);
    }
//https://huan1993.iteye.com/blog/2425265
    //RemindingNotifier



    @Override
    protected Mono<Void> doNotify(InstanceEvent instanceEvent, Instance instance) {
        return Mono.fromRunnable(new Runnable() {
            @Override
            public void run() {
                String type = instanceEvent.getType();
                //  STATUS_CHANGED
                if("STATUS_CHANGED".equals(type)){
                    String status = instance.getStatusInfo().getStatus();
                    Instant statusTimestamp = instance.getStatusTimestamp();
                    String name = instance.getRegistration().getName();
                    String serviceUrl = instance.getRegistration().getServiceUrl();

                    System.out.println("-----------------start doNotify ---------------------------");
                    System.out.println("instance name:"+name+"; instance url:"+serviceUrl+"; status:"+status+"; time:"+statusTimestamp);
                    System.out.println("-----------------end doNotify ---------------------------");
                }
            }
        });
    }

    @Override
    protected boolean shouldNotify(InstanceEvent event, Instance instance) {
        return super.shouldNotify(event, instance);
    }
}
