package hr.algebra.spacex.data.mapper

import hr.algebra.spacex.data.local.LaunchEntity
import hr.algebra.spacex.data.remote.dto.launches.LaunchDtoItem
import hr.algebra.spacex.domain.model.Launch

fun LaunchEntity.toLaunch(): Launch {
    return Launch(
        _id = _id,
        launchDate = launchDate,
        name = name,
        details = details,
        launchImagePath =  launchImagePath
    )
}

/*fun Launch.toLaunchEntity(): LaunchEntity {
    return LaunchEntity(
        _id = _id,
        launchDate = launchDate,
        name = name,
        details = details,
        launchImagePath =  launchImagePath
    )
}*/

fun LaunchDtoItem.toLaunchEntity(): LaunchEntity {
    return LaunchEntity(
        _id = null,
        launchDate = this.dateLocal,
        name = this.name,
        details = this.details,
        launchImageUrl = this.links.flickr.original!!.first(),
        launchImagePath = null,
    )
}