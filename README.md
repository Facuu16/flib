# Flib

A modular development library for Minecraft server plugins built for Paper and Spigot.

Flib provides reusable infrastructure components commonly required when building complex plugins, reducing boilerplate and encouraging clean architecture patterns.

## Overview

Flib was designed to accelerate plugin development by consolidating common patterns such as dependency injection, configuration management, command systems, and data persistence into a unified framework.

The library has been used as the foundation for multiple production plugins running on a live multiplayer server.

## Key Features

- **Dependency Injection**
  Annotation-based component system using `@Bind` and `@Inject`.

- **Service Lifecycle Management**
  Built-in lifecycle hooks for starting and stopping services cleanly.

- **Data Persistence**
  Repository pattern with JSON storage powered by Jackson and Caffeine caching for efficient data access.

- **Command Framework**
  Integration with ACF and Cloud command frameworks for flexible command handling.

- **Configuration Management**
  Support for multiple formats including YAML, HOCON, and Properties using Configurate.

- **Inventory GUI API**
  Programmatic creation of interactive inventory-based interfaces.

- **Prompt System**
  Conversation-style player prompts for handling multi-step user input.

- **Discord Integration**
  Optional support for Discord bots and commands.

- **Placeholder Expansion**
  Integration with PlaceholderAPI for dynamic placeholders.

## Technical Highlights

- Multi-module Gradle project with shaded JAR distribution
- Classpath scanning with ClassGraph for automatic component registration
- High-performance caching with Caffeine
- Modern Minecraft chat formatting using the Adventure API
- Designed for maintainability and extensibility
- Compatible with Java 8

## Technologies

- **Language:** Java
- **Build Tool:** Gradle
- **Dependency Injection:** Unnamed Team Inject
- **Serialization:** Jackson
- **Caching:** Caffeine
- **Configuration:** Configurate
- **Commands:** ACF + Cloud
- **Chat API:** Adventure Platform
- **Classpath Scanning:** ClassGraph

## Motivation

Developing large Minecraft plugins often requires repeatedly implementing infrastructure such as command systems, configuration loading, and persistent storage.

Flib centralizes these concerns into reusable modules, allowing plugin developers to focus on gameplay logic rather than rebuilding foundational systems for every project.