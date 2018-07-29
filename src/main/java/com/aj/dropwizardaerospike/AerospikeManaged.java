package com.aj.dropwizardaerospike;

import com.aerospike.client.AerospikeClient;
import io.dropwizard.lifecycle.Managed;

public class AerospikeManaged implements Managed {

    private AerospikeClient aerospikeClient;

    public AerospikeManaged(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        aerospikeClient.close();
    }
}
