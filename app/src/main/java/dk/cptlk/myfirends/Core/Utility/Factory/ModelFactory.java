package dk.cptlk.myfirends.Core.Utility.Factory;

import android.content.Context;

import dk.cptlk.myfirends.Client.Internal.Model.Model;
import dk.cptlk.myfirends.Core.ClientAdapter.IModel;
import dk.cptlk.myfirends.Core.DataAdapter.IRepository;
import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.Core.Services.IFriendService;
import dk.cptlk.myfirends.Core.Services.Impl.FriendService;
import dk.cptlk.myfirends.Data.Internal.DB.Helper.FriendDbHelper;
import dk.cptlk.myfirends.Data.Internal.Repositories.FriendRepoNonePersistent;
import dk.cptlk.myfirends.Data.Internal.Repositories.FriendRepoPersistent;


public class ModelFactory {


    public static IModel BuildModel(Client c, Data d, Context ctx) throws Exception {
        IModel model;

        IRepository<Friend> friendRepo;
        switch (d) {
            case PERSISTENT_INTERNAL:
                friendRepo = new FriendRepoPersistent(new FriendDbHelper(ctx));
                break;
            case NONPERSISTENT_INTERNAL:
                friendRepo = new FriendRepoNonePersistent();
                break;
            default:
                throw new IllegalStateException();
        }
        IFriendService friendService = new FriendService(friendRepo);
        switch (c) {
            case ANDROID_INTERNAL:
                model = new Model(friendService);
                break;
            default:
                throw new IllegalStateException();
        }
        return model;
    }
}
