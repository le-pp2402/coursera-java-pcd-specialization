package edu.coursera.concurrent;

import edu.rice.pcdp.Actor;
import static edu.rice.pcdp.PCDP.finish;

import java.util.ArrayList;

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
        final SieveActorActor sa = new SieveActorActor(2);
        finish(() -> {
            for (int i = 3; i <= limit; i += 2) {
                sa.send(i);
            }
        });

        int ans = 0;
        SieveActorActor current = sa;
        while (current != null) {
            ans += current.ar.size();
            current = current.nextActor;
        }

        return ans;
    }

    /**
     * An actor class that helps implement the Sieve of Eratosthenes in
     * parallel.
     */
    public static final class SieveActorActor extends Actor {
        private final int MX_LEN = 3;
        private ArrayList<Integer> ar = new ArrayList<>();
        SieveActorActor nextActor;

        public SieveActorActor(int prime) {
            ar.add(prime);
            nextActor = null;
        }

        /**
         * Process a single message sent to this actor.
         *
         * @param msg Received message
         */
        @Override
        public void process(final Object msg) {
            int cad = (Integer) msg;

            for (Integer p : ar) {
                if (cad % p == 0)
                    return;
            }

            if (ar.size() < MX_LEN) {
                ar.add(cad);
            } else {
                if (nextActor != null) {
                    nextActor.send(cad);
                } else {
                    nextActor = new SieveActorActor(cad);
                }
            }
        }
    }
}
