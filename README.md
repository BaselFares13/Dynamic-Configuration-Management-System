# Dynamic Configuration Management System

## Overview

This project implements a **Dynamic Configuration Management System** for a cloud-based application using **Java**.

The system manages configuration parameters that control application behavior, such as retry limits, SMS limits, service URLs, and other runtime settings.

It supports **multiple environments**, **dynamic overrides**, **version history tracking**, and **JSON export for deployment**.

---

## Scenario

Modern cloud applications require flexible configuration management.
Different environments (such as **DEV**, **QA**, and **PROD**) often require different configuration values.

This system allows administrators and services to manage configuration parameters dynamically while keeping track of changes and ensuring correct value types.

---

## Features

### 1. Parameter Management

The system allows users to:

* Create configuration parameters
* Update existing parameters
* Delete parameters

Each parameter contains:

* **Name** – unique identifier for the parameter
* **Type** – supported types include:

  * `STRING`
  * `NUMBER`
  * `BOOLEAN`
* **Default Value**
* **Environment** (e.g., DEV, QA, PROD)

---

### 2. Dynamic Overrides

Parameters can be **temporarily overridden**.

* If an **override value exists**, it is used by the system.
* If no override exists, the **default value** is used.

This allows services or administrators to temporarily adjust system behavior without modifying the original configuration.

---

### 3. Version History Tracking

Every time a parameter changes, the system stores a **version history record** containing:

* Parameter name
* Environment
* Old value
* New value
* User who made the change
* Timestamp of the change

This provides a **complete audit trail** of configuration modifications.

---

### 4. Search Functionality

The system supports searching parameters using:

* **Environment** (e.g., DEV, QA, PROD)
* **Type** (`STRING`, `NUMBER`, `BOOLEAN`)
* **Partial Name Match** (case-insensitive)

This makes it easy to locate specific configuration parameters.

---

### 5. JSON Export

The configuration can be exported to **pretty formatted JSON** for deployment purposes.

This allows external systems or deployment pipelines to easily consume the configuration.

Example output:

```json
{
  "DEV" : [ {
    "id" : "a5795e85-5621-46c9-90dd-349be472feac",
    "name" : "smsLimit",
    "type" : "NUMBER",
    "defaultValue" : "90",
    "environment" : "DEV",
    "overriddenValue" : null,
    "lastUpdated" : [ 2026, 3, 16, 13, 38, 40, 908472000 ],
    "value" : "90",
    "valueOverridden" : false
  } ]
}
```

---

### 6. Input Validation

The system validates input values before saving them.

Examples:

* `NUMBER` must contain a numeric value.
* `BOOLEAN` must contain `true` or `false`.
* `STRING` must contain a valid string.

Invalid inputs will result in an exception to prevent corrupted configuration data.

