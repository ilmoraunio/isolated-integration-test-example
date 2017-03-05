# Isolated integration test example

Demonstrates the usage of `session_replication_role` in integration tests through simple examples.

## Usage

Prerequisites:

- postgresql (tested on 9.4)
- postgresql database `isolated-integration-test`
- postgresql superuser `isolated-integration-test` with same password
- install `postgresql-contrib` matching your current postgres version as dependency

To run database migrations:

```
lein migrate
```

Pure SQL example found inside `resources/migrations/005-Message-insert-example.edn`

#### Reports

A naive introducible problem is to add a field with default value to `Users` and check the report with `lein midje` using the fact-group metadata.

```sql
ALTER TABLE Users ADD COLUMN foo TEXT NOT NULL DEFAULT 'bar';
```

```bash
# to run tests
lein midje :all
# or just
lein midje

# with metadata filtering
lein midje :filter integration
lein midje :filter integration-isolated
```

Noticeably, tests run with `integration-isolated` metadata provide less noise.

It's only by changing the validation schema that this problem disappears.

By adding the kw `:foo` and its associated type into the original definition, the problem is fixed.


```clojure
;; src/isolated_integration_test/db/user.clj
(s/defschema New {:username String
                  :password String
                  :foo String}) ;; <-- add this and you're good to go
```

## License

Copyright © Solita

Released under the MIT license.
