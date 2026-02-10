# utils
Zero-dependencies KMP utils and extensions.

## Environment
- Kotlin 2.3.0
- JDK 11

## Main features

---
### Common module
#### Extensions and top-level functions
- Iterable extensions:
  - `forEachLet()`
  - `Map.filterKeysNotNull()`
- Duration extensions:
  - `Duration.ms`
  - `Long.ms`
  - `Int.ms`
- String extensions and utils:
  - `generateRandomString()`
  - `String.wrap()`
  - `String.takeOrCut()`
  - `Number.bytesToString()`
  - `uuid()` \[js, jvm\]
- Declension of endings in Russian words:
  - `endWordForNumWithNumber()`
  - `Number.endOfWord()`
- Idiomatic functions:
  - `letIf()`
  - `alsoIf()`
  - `applyIf()`
- Infix functions:
  - `notNull`
  - `orNotNull`
  - `orNull`
  - `bothNull`
- Others:
  - `Double.format()` \[js, jvm\]
  - `Double.round()` \[js, jvm\]
  - `orNullIf()`
  - `ifNotEmpty()`
  - `Boolean.outcome()`

#### Utils
- `ProgressTracker` - A utility class to track and notify progress percentage based on iterations over a set number of 
elements
---

### Jvm module
#### Extensions and top-level functions:
- Encryption:
  - `String.hmac()`
  - `String.hmacSHA256()`
  - `String.hmacSHA512()`
  - `randomHexString()`
  - `String.encryptAesGcm()`
  - `String.decryptAesGcm()`
- Date and Time Utils:
  - `timeZone` - property that depends on the `TIME_ZONE` environment variable (or property) or uses the system time 
zone by default. Use in most of the date and time extensions.
  - `now()`
  - `nowTime()`
  - `today()`
  - `yesterday()`
  - `tomorrow()`
  - `nowZoned()`
  - `LocalDateTime.atZone()`
  - `LocalDate.firstDayOfYear()`
  - `LocalDate.firstDayOfMonth()`
  - `LocalDate.lastDayOfYear()`
  - `LocalDate.atEndOfDay()`
  - `LocalDate.toDate()`
  - `LocalDate.lastDayOfMonth()`
  - `LocalDateTime.startOfDay()`
  - `LocalDateTime.endOfDay()`
  - `LocalDateTime.toDate()`
  - `LocalTime.inPeriod()`
- Others:
  - `Charsets.WINDOWS_1251`
  - `String.capitalize()`

#### Utils
- `PropertiesFactory` - Contains factory methods for getting properties from files
  - `String.asResourceStream()`
  - `String.asResource()`
- `Cacheable` - A delegate that persists the value with cache duration 
- `SyncDelegate` - A delegate that synchronizes access to the value
  - `Delegates.sync()`
