function Rule=CLIP4(varargin)
%%

if strcmp(varargin{2},'Binary')
   Rule=Binary(varargin{1},varargin{3});
else
    Rule=MultiClass(varargin{1});
end
         