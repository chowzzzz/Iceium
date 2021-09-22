/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.TagEntity;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagExistException;
import util.exception.TagNotFoundException;
import java.util.List;
import javax.ejb.Local;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateTagException;

/**
 *
 * @author Theodoric
 */
@Local
public interface TagEntitySessionBeanLocal
{

    public TagEntity createNewTag(TagEntity newTagEntity) throws TagExistException, UnknownPersistenceException, InputDataValidationException;

    public List<TagEntity> retrieveAllTags();

    public List<TagEntity> retrieveAllTagsWithProducts();

    public TagEntity retrieveTagByTagId(Long tagId) throws TagNotFoundException;

    public TagEntity retrieveTagByName(String name) throws TagNotFoundException;
    
    public void updateTag(TagEntity tagEntity) throws UpdateTagException, InputDataValidationException, TagExistException, NoChangesMadeException;
    
    public void deleteTag(Long tagId) throws DeleteTagException;
} 
