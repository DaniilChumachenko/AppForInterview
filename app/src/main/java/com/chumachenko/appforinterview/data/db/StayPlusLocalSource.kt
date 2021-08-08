package com.chumachenko.appforinterview.data.db

import com.chumachenko.appforinterview.data.db.realmModel.StayPlusItemRealm
import com.chumachenko.appforinterview.data.repository.model.StayPlusItem
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class StayPlusLocalSource @Inject constructor(val gson: Gson) :
    StayPlusDataSource<StayPlusItemRealm, StayPlusItem> {

    private val realmConfiguration: RealmConfiguration? = Realm.getDefaultConfiguration()
    private val realmEntity: Class<StayPlusItemRealm> = StayPlusItemRealm::class.java

    override fun search(search: String, isFollowing: Boolean): Maybe<List<StayPlusItem>> {
        val realm = Realm.getInstance(realmConfiguration!!)
        val managedEntities: List<StayPlusItemRealm> = realm.where(realmEntity)
            .equalTo("isFollowing", isFollowing)
            .and()
            .contains("name", search, Case.INSENSITIVE)
            .limit(20)
            .findAll()

        val unmanagedEntities = realm.copyFromRealm(managedEntities)

        realm.close()

        return if (unmanagedEntities.isEmpty()) Maybe.empty() else Maybe.just(unmanagedEntities)
            .map { items ->
                val list: ArrayList<StayPlusItem> = ArrayList()
                items.forEach { item -> list.add(item.toStayPlusItem()) }
                list
            }
    }

    override fun findById(id: String?): Maybe<StayPlusItem> {
        val realm: Realm = Realm.getInstance(realmConfiguration!!)

        val managedModel: StayPlusItemRealm = realm.where(realmEntity)
            .equalTo("id", id)
            .findFirst()
            ?: return Maybe.empty()

        val unmanagedModel: StayPlusItemRealm = realm.copyFromRealm(managedModel)

        realm.close()

        return Maybe.just(unmanagedModel)
            .subscribeOn(Schedulers.io())
            .map { item ->
                item.toStayPlusItem()
            }
    }

    override fun findByIdSync(id: String): StayPlusItem? {
        val realm: Realm = Realm.getInstance(realmConfiguration!!)

        val managedModel: StayPlusItemRealm = realm.where(realmEntity)
            .equalTo("id", id)
            .findFirst()
            ?: return null

        val unmanagedModel: StayPlusItemRealm = realm.copyFromRealm(managedModel)

        realm.close()

        return unmanagedModel.toStayPlusItem()
    }

    override fun findByIdRealmObject(itemId: String): StayPlusItemRealm? {
        val realm: Realm = Realm.getInstance(realmConfiguration!!)

        val managedModel: StayPlusItemRealm? = realm.where(realmEntity)
            .equalTo("id", itemId)
            .findFirst()
        var model: StayPlusItemRealm? = null
        managedModel?.let {
            model = realm.copyFromRealm(it)
        }
        realm.close()
        return model
    }


    override fun findByIds(ids: List<String>?): Maybe<List<StayPlusItem>> {
        val realm = Realm.getInstance(realmConfiguration!!)
        realm.refresh()

        val managedModels: List<StayPlusItemRealm>? = realm.where(realmEntity)
            .`in`("id", ids?.toTypedArray())
            .findAll()

        val unmanagedModels = realm.copyFromRealm(managedModels!!)

        realm.close()

        return if (unmanagedModels.isEmpty()) {
            Maybe.empty()
        } else {

            Maybe.just(unmanagedModels)
                .map { items ->
                    val list: ArrayList<StayPlusItem> = ArrayList()
                    items.forEach { item -> list.add(item.toStayPlusItem()) }
                    list
                }
        }
    }

    override fun addOrReplace(model: StayPlusItemRealm): Maybe<StayPlusItem> {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.createOrUpdateObjectFromJson(
                        realmEntity,
                        gson.toJson(model)
                    )
                }
                realmInstance.close()
                return Maybe.just(model.toStayPlusItem())
            }
        } catch (error: Exception) {
            error.printStackTrace()
            return Maybe.error(error)
        }
    }

    override fun addOrReplace(models: List<StayPlusItemRealm>?): Maybe<List<StayPlusItem>> {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.createOrUpdateAllFromJson(
                        realmEntity,
                        gson.toJson(models)
                    )
                }
                realmInstance.close()
                return Maybe.just(models ?: ArrayList()).map { items ->
                    val list: ArrayList<StayPlusItem> = ArrayList()
                    items.forEach { item -> list.add(item.toStayPlusItem()) }
                    list
                }
            }
        } catch (error: Exception) {
            error.printStackTrace()
            return Maybe.error(error)
        }
    }

    override fun deleteAll(): Completable {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.where(realmEntity).findAll().deleteAllFromRealm()
                }
            }
        } catch (e: Error) {
            e.printStackTrace()
            return Completable.error(e)
        }
        return Completable.defer { Completable.complete() }
    }

    override fun delete(models: ArrayList<StayPlusItemRealm>?): Completable {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransactionAsync { realm: Realm ->
                    realm.where(realmEntity)
                        .`in`("id", getIds(models ?: ArrayList()))
                        .findAll()
                        .deleteAllFromRealm()
                }
            }
        } catch (e: Error) {
            e.printStackTrace()
            return Completable.error(e)
        }
        return Completable.defer { Completable.complete() }
    }

    override fun delete(id: String): Completable {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.where(realmEntity)
                        .equalTo("id", id)
                        .findAll()
                        .deleteAllFromRealm()
                }
            }
        } catch (e: Error) {
            e.printStackTrace()
            return Completable.error(e)
        }
        return Completable.defer { Completable.complete() }
    }

    override fun deleteSync(id: String) {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.where(realmEntity)
                        .equalTo("id", id)
                        .findAll()
                        .deleteAllFromRealm()
                }
            }
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    private fun getIds(models: List<StayPlusItemRealm>): Array<String> {
        return models.map { it.image }.toTypedArray()
    }

    override fun addOrReplaceSync(model: StayPlusItemRealm): StayPlusItem {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.createOrUpdateObjectFromJson(
                        realmEntity,
                        gson.toJson(model)
                    )
                }
                realmInstance.close()
                return model.toStayPlusItem()
            }
        } catch (error: Exception) {
            error.printStackTrace()
            throw error
        }
    }

    override fun findAll(): Maybe<ArrayList<StayPlusItem>> {
        val realm = Realm.getInstance(realmConfiguration!!)
        realm.refresh()

        val managedModels: List<StayPlusItemRealm>? = realm.where(realmEntity)
            .findAll()

        val unmanagedModels = realm.copyFromRealm(managedModels!!)

        realm.close()

        return if (unmanagedModels.isEmpty()) {
            Maybe.empty()
        } else {

            Maybe.just(unmanagedModels)
                .map { items ->
                    val list: ArrayList<StayPlusItem> = ArrayList()
                    items.forEach { item -> list.add(item.toStayPlusItem()) }
                    list
                }
        }
    }

}