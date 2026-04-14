package edu.coursera.concurrent;

import edu.rice.pcdp.Actor;
import static edu.rice.pcdp.PCDP.finish;

/**
 * An actor-based implementation of the Sieve of Eratosthenes.
 *
 * TODO Fill in the empty SieveActorActor actor class below and use it from
 * countPrimes to determin the number of primes <= limit.
 */
public final class SieveActor extends Sieve {
    /**
     * {@inheritDoc}
     *
     * TODO Use the SieveActorActor class to calculate the number of primes <=
     * limit in parallel. You might consider how you can model the Sieve of
     * Eratosthenes as a pipeline of actors, each corresponding to a single
     * prime number.
     */
    @Override
    public int countPrimes(final int limit) {
        if (limit < 2)
            return 0;
        SieveActorActor sa = new SieveActorActor(2);
        finish(() -> {
            for (int i = 3; i <= limit; i += 2) {
                sa.send(i);
            }
        });

        int ans = 0;
        while (sa != null) {
            ++ans;
            sa = sa.nextActor;
        }

        return ans;
    }

    /**
     * An actor class that helps implement the Sieve of Eratosthenes in
     * parallel.
     */
    public static final class SieveActorActor extends Actor {
        private int localPrimes;
        SieveActorActor nextActor;

        public SieveActorActor(int localPrimes) {
            this.localPrimes = localPrimes;
            nextActor = null;
        }

        /**
         * Process a single message sent to this actor.
         *
         * TODO complete this method.
         *
         * @param msg Received message
         */
        @Override
        public void process(final Object msg) {
            int cad = (Integer) msg;

            if (cad % localPrimes == 0)
                return;

            if (nextActor != null) {
                nextActor.send(cad);
            } else {
                nextActor = new SieveActorActor(cad);
            }
        }
    }
}
