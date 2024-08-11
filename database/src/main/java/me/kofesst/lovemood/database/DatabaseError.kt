package me.kofesst.lovemood.database

abstract class DatabaseException : Exception()

class ProfileNotFoundException : DatabaseException()
class RelationshipNotFoundException : DatabaseException()
class RelationshipEventNotFoundException : DatabaseException()