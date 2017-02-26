# Isolated integration test example

Demonstrates the usage of `session_replication_role` in integration tests through simple examples.

## Usage

To run tests:

```
lein midje :print-facts
lein midje :print-facts :filter integration
lein midje :print-facts :filter integration-isolated
```

Prerequisites:

- postgresql (tested on 9.4)
- postgresql database `isolated-integration-test`
- postgresql superuser `isolated-integration-test` with same password
- install `postgresql-contrib` matching your current postgres version as dependency

Pure SQL example found inside `resources/migrations/005-Message-insert-example.edn`

## License

Copyright © Solita

Released under the MIT license.
