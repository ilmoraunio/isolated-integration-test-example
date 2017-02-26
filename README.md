# Isolated integration test example

Demonstrates the usage of `session_replication_role` in integration tests.

## Usage

Run tests with:

`lein test` or `lein midje`

Isolated integration test can be found inside `test/isolated_integration_test/db/message_test.clj`

Prerequisites:

- postgresql (tested on 9.4)
- postgresql database `isolated-integration-test`
- postgresql superuser `isolated-integration-test` with same password
- install `postgresql-contrib` matching your current postgres version as dependency

Pure SQL example found inside `resources/migrations/005-Message-insert-example.edn`

## License

Copyright © Solita

Released under the MIT license.
